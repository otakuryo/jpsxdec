/*
 * jPSXdec: PlayStation 1 Media Decoder/Converter in Java
 * Copyright (C) 2007-2010  Michael Sabin
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

/** For audio sectors in Square games: FF8, FF9, and Chrono Cross. 
 *  Used by SquareAudioPullDemuxerDecoderIS, and PSXMedia playing. */
public interface ISquareAudioSector extends IIdentifiedSector {

    /** Audio samples/second. */
    int getSamplesPerSecond();
    /** How many left/right-channel audio samples will be decoded from this 
     *  audio sector. */
    public long getLeftSampleCount();
    /** How many left/right-channel audio samples will be decoded from this
     *  audio sector. */
    public long getRightSampleCount();
    
    /** 0 for left channel, 1 for right channel */
    int getAudioChannel();
        
    /** Number of bytes of audio data in the Square ADPCM format. */
    int getAudioDataSize();
    /** Frame number that this sector belongs to. */
    int getFrameNumber();
    /** Number of chunks of audio data in this frame (should always be 2). */
    int getAudioChunksInFrame();
    /** The chunk number of this audio sector (either 0 or 1). */
    int getAudioChunkNumber();
    
    boolean matchesPrevious(ISquareAudioSector oPrevSect);
    
}