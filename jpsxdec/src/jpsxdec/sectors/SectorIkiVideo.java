/*
 * jPSXdec: PlayStation 1 Media Decoder/Converter in Java
 * Copyright (C) 2007-2017  Michael Sabin
 * All rights reserved.
 *
 * Redistribution and use of the jPSXdec code or any derivative works are
 * permitted provided that the following conditions are met:
 *
 *  * Redistributions may not be sold, nor may they be used in commercial
 *    or revenue-generating business activities.
 *
 *  * Redistributions that are modified from the original source must
 *    include the complete source code, including the source code for all
 *    components used by a binary built from the modified sources. However, as
 *    a special exception, the source code distributed need not include
 *    anything that is normally distributed (in either source or binary form)
 *    with the major components (compiler, kernel, and so on) of the operating
 *    system on which the executable runs, unless that component itself
 *    accompanies the executable.
 *
 *  * Redistributions must reproduce the above copyright notice, this list
 *    of conditions and the following disclaimer in the documentation and/or
 *    other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jpsxdec.sectors;

import javax.annotation.Nonnull;
import jpsxdec.cdreaders.CdSector;
import jpsxdec.cdreaders.CdxaSubHeader.SubMode;
import jpsxdec.i18n.I;
import jpsxdec.psxvideo.bitstreams.BitStreamUncompressor_Iki;
import jpsxdec.psxvideo.bitstreams.BitStreamUncompressor_STRv2;
import jpsxdec.psxvideo.mdec.MdecException;
import jpsxdec.util.BinaryDataNotRecognized;
import jpsxdec.util.IO;
import jpsxdec.util.LocalizedIncompatibleException;


public class SectorIkiVideo extends SectorAbstractVideo implements IVideoSectorWithFrameNumber {
    
    // .. Fields ..........................................................

    // Magic is normal STR = 0x80010160
    private final CommonVideoSectorFirst16bytes _header = new CommonVideoSectorFirst16bytes();
    private int  _iWidth;                //  16   [2 bytes]
    private int  _iHeight;               //  18   [2 bytes]
    private int  _iRunLengthCodeCount;   //  20   [2 bytes]
    // 0x3800                            //  22   [2 bytes]
    // Width again                       //  24   [2 bytes]
    // Height again                      //  26   [2 bytes]
    private long _lngFourZeros;          //  28   [4 bytes]
    //   32 TOTAL

    @Override
    protected int getSectorHeaderSize() { return 32; }
    
    public SectorIkiVideo(@Nonnull CdSector cdSector) {
        super(cdSector);
        if (isSuperInvalidElseReset()) return;
        
        // only if it has a sector header should we check if it reports DATA or VIDEO
        if (cdSector.hasSubHeader() &&
            cdSector.subModeMask(SubMode.MASK_DATA | SubMode.MASK_VIDEO) == 0)
        {
            return;
        }

        _header.readMagic(cdSector);
        if (_header.lngMagic != SectorStrVideo.VIDEO_SECTOR_MAGIC) return;
        if (_header.readChunkNumberStandard(cdSector)) return;
        if (_header.readChunksInFrameStandard(cdSector)) return;
        if (_header.readFrameNumberStandard(cdSector)) return;
        if (_header.readUsedDemuxSizeStandard(cdSector)) return;
        _iWidth = cdSector.readSInt16LE(16);
        if (_iWidth < 1) return;
        _iHeight = cdSector.readSInt16LE(18);
        if (_iHeight < 1) return;
        _iRunLengthCodeCount = cdSector.readUInt16LE(20);
        if (_iRunLengthCodeCount < 1) return;
        int iMagic3800 = cdSector.readUInt16LE(22);
        if (iMagic3800 != 0x3800) return;
        int iWidth = cdSector.readSInt16LE(24);
        if (iWidth != _iWidth) return;
        int iHeight = cdSector.readUInt16LE(26);
        if (iHeight != _iHeight) return;
        _lngFourZeros = cdSector.readUInt32LE(28);
        if (_lngFourZeros != 0) return;

        setProbability(100);
    }

    // .. Public methods ...................................................

    public @Nonnull String getTypeName() {
        return "IKI Video";
    }

    public String toString() {
        return String.format("%s %s frame:%d chunk:%d/%d %dx%d " +
            "{demux frame size=%d rlc=%d ??=%08x}",
            getTypeName(),
            super.cdToString(),
            _header.iFrameNumber,
            _header.iChunkNumber,
            _header.iChunksInThisFrame,
            _iWidth,
            _iHeight,
            _header.iUsedDemuxedSize,
            _iRunLengthCodeCount,
            _lngFourZeros
            );
    }

    public int getChunkNumber() {
        return _header.iChunkNumber;
    }

    public int getChunksInFrame() {
        return _header.iChunksInThisFrame;
    }

    public int getFrameNumber() {
        return _header.iFrameNumber;
    }

    public int getHeight() {
        return _iHeight;
    }

    public int getWidth() {
        return _iWidth;
    }


    @Override
    public int checkAndPrepBitstreamForReplace(@Nonnull byte[] abDemuxData, int iUsedSize,
                                               int iMdecCodeCount, @Nonnull byte[] abSectUserData)
            throws LocalizedIncompatibleException
    {
        final int[] aiDimensions;
        try {
            aiDimensions = BitStreamUncompressor_Iki.getDimensions(abDemuxData);
        } catch (BinaryDataNotRecognized ex) {
            throw new LocalizedIncompatibleException(I.REPLACE_FRAME_TYPE_NOT_IKI(), ex);
        }
        int iWidth = aiDimensions[0];
        int iHeight = aiDimensions[1];
        if (iWidth != _iWidth || iHeight != _iHeight) {
            throw new LocalizedIncompatibleException(I.REPLACE_FRAME_IKI_DIMENSIONS_MISMATCH(
                                            iWidth, iHeight, _iWidth, _iHeight));
        }

        IO.writeInt16LE(abDemuxData, 4, (short)_iWidth);
        IO.writeInt16LE(abDemuxData, 6, (short)_iHeight);
        
        int iDemuxSizeForHeader = (iUsedSize + 3) & ~3;

        IO.writeInt32LE(abSectUserData, 12, iDemuxSizeForHeader);
        IO.writeInt16LE(abSectUserData, 20,
                BitStreamUncompressor_STRv2.calculateHalfCeiling32(iMdecCodeCount));

        return getSectorHeaderSize();
    }

}

