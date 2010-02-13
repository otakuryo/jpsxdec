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

package jpsxdec.plugins.psx.video.encode;

import jpsxdec.plugins.psx.video.mdec.MdecInputStream;
import jpsxdec.plugins.psx.video.mdec.MdecDecoder_double;
import jpsxdec.plugins.psx.video.decode.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import jpsxdec.BetterFileChooser;
import jpsxdec.plugins.psx.video.PsxYuvImage;
import jpsxdec.formats.RgbIntImage;
import jpsxdec.plugins.psx.video.DemuxImage;
import jpsxdec.plugins.psx.video.decode.UncompressionException;
import jpsxdec.plugins.psx.video.encode.ParsedMdecImage.*;
import jpsxdec.plugins.psx.video.mdec.idct.StephensIDCT;

/**
 *
 * @author Michael
 */
public class DemuxQualityEditor extends javax.swing.JFrame {

    private static final int DEMUX_WIDTH = 640;
    private static final int DEMUX_HEIGHT = 192;
    private static final int FF7_FRAME = 1;
    private static final int STRV2_FRAME = 2;
    private static final int FRAME_TYPE = STRV2_FRAME;

    private String m_sSourceFile;
    private ParsedMdecImage m_oUncompressedImage;
    private int m_iWidth;
    private int m_iHeight;
    private MdecDecoder_double m_oMacBlockDecoder = new MdecDecoder_double(new StephensIDCT(), 16, 16);
    private int m_iOriginalDemuxSize;
    private int m_iOriginalMdecCount;

    /** Creates new form DemuxQualityEditor */
    public DemuxQualityEditor() {
        initComponents();

        m_iWidth = DEMUX_WIDTH;
        m_iHeight = DEMUX_HEIGHT;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        m_guiImage = new jpsxdec.plugins.psx.video.encode.ImagePanel();
        m_guiOpen = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        m_guiY1 = new jpsxdec.plugins.psx.video.encode.ImagePanel();
        m_guiY2 = new jpsxdec.plugins.psx.video.encode.ImagePanel();
        m_guiY3 = new jpsxdec.plugins.psx.video.encode.ImagePanel();
        m_guiY4 = new jpsxdec.plugins.psx.video.encode.ImagePanel();
        m_guiCr = new jpsxdec.plugins.psx.video.encode.ImagePanel();
        m_guiCb = new jpsxdec.plugins.psx.video.encode.ImagePanel();
        m_guiDemuxSize = new javax.swing.JLabel();
        m_guiMdecCount = new javax.swing.JLabel();
        m_guiQscaleSpin = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        m_guiImage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_guiImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                m_guiImageMousePressed(evt);
            }
        });

        javax.swing.GroupLayout m_guiImageLayout = new javax.swing.GroupLayout(m_guiImage);
        m_guiImage.setLayout(m_guiImageLayout);
        m_guiImageLayout.setHorizontalGroup(
            m_guiImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        m_guiImageLayout.setVerticalGroup(
            m_guiImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 281, Short.MAX_VALUE)
        );

        m_guiOpen.setText("Open");
        m_guiOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_guiOpenActionPerformed(evt);
            }
        });

        jButton2.setText("Save");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        m_guiY1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_guiY1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                m_guiY1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout m_guiY1Layout = new javax.swing.GroupLayout(m_guiY1);
        m_guiY1.setLayout(m_guiY1Layout);
        m_guiY1Layout.setHorizontalGroup(
            m_guiY1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        m_guiY1Layout.setVerticalGroup(
            m_guiY1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        m_guiY2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_guiY2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                m_guiY2MousePressed(evt);
            }
        });

        javax.swing.GroupLayout m_guiY2Layout = new javax.swing.GroupLayout(m_guiY2);
        m_guiY2.setLayout(m_guiY2Layout);
        m_guiY2Layout.setHorizontalGroup(
            m_guiY2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        m_guiY2Layout.setVerticalGroup(
            m_guiY2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        m_guiY3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_guiY3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                m_guiY3MousePressed(evt);
            }
        });

        javax.swing.GroupLayout m_guiY3Layout = new javax.swing.GroupLayout(m_guiY3);
        m_guiY3.setLayout(m_guiY3Layout);
        m_guiY3Layout.setHorizontalGroup(
            m_guiY3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        m_guiY3Layout.setVerticalGroup(
            m_guiY3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        m_guiY4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_guiY4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                m_guiY4MousePressed(evt);
            }
        });

        javax.swing.GroupLayout m_guiY4Layout = new javax.swing.GroupLayout(m_guiY4);
        m_guiY4.setLayout(m_guiY4Layout);
        m_guiY4Layout.setHorizontalGroup(
            m_guiY4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        m_guiY4Layout.setVerticalGroup(
            m_guiY4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        m_guiCr.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_guiCr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                m_guiCrMousePressed(evt);
            }
        });

        javax.swing.GroupLayout m_guiCrLayout = new javax.swing.GroupLayout(m_guiCr);
        m_guiCr.setLayout(m_guiCrLayout);
        m_guiCrLayout.setHorizontalGroup(
            m_guiCrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        m_guiCrLayout.setVerticalGroup(
            m_guiCrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        m_guiCb.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        m_guiCb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                m_guiCbMousePressed(evt);
            }
        });

        javax.swing.GroupLayout m_guiCbLayout = new javax.swing.GroupLayout(m_guiCb);
        m_guiCb.setLayout(m_guiCbLayout);
        m_guiCbLayout.setHorizontalGroup(
            m_guiCbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        m_guiCbLayout.setVerticalGroup(
            m_guiCbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(m_guiY1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_guiY2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_guiCr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(m_guiY3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_guiY4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_guiCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(m_guiCr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(m_guiCb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(m_guiY1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_guiY2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(m_guiY3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_guiY4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        m_guiDemuxSize.setText("Demux size");

        m_guiMdecCount.setText("MDEC count");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(m_guiImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(m_guiOpen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))
                            .addComponent(m_guiDemuxSize)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(m_guiQscaleSpin, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(m_guiMdecCount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(37, 37, 37)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(m_guiImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(m_guiOpen)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_guiDemuxSize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_guiMdecCount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_guiQscaleSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void m_guiOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_guiOpenActionPerformed
        try {

            JFileChooser fc = new BetterFileChooser("..\\..\\FF7-sub");
            fc.setDialogTitle("Open");

            fc.setFileFilter(new FileNameExtensionFilter("demux (*.demux)", "demux"));
            int ret = fc.showOpenDialog(this);
            if (ret == JFileChooser.APPROVE_OPTION) {
                DemuxImage oDemux = new DemuxImage(m_iWidth, m_iHeight, -1, fc.getSelectedFile());
                m_oUncompressedImage = new ParsedMdecImage(m_iWidth, m_iHeight);

                if (FRAME_TYPE == FF7_FRAME) {
                    DemuxFrameUncompressor_FF7 oUncompressor = new DemuxFrameUncompressor_FF7(oDemux.getData(), 0);
                    m_oUncompressedImage.readFrom(oUncompressor);

                    int iQscale = m_oUncompressedImage.getMacroBlock(0, 0).Y1.getQscale();
                    m_guiQscaleSpin.setValue(iQscale);
                } else if (FRAME_TYPE == STRV2_FRAME) {
                    DemuxFrameUncompressor_STRv2 oUncompressor = new DemuxFrameUncompressor_STRv2(oDemux.getData(), 0);
                    m_oUncompressedImage.readFrom(oUncompressor);

                    int iQscale = m_oUncompressedImage.getMacroBlock(0, 0).Y1.getQscale();
                    m_guiQscaleSpin.setValue(iQscale);
                }

                m_iOriginalMdecCount = m_oUncompressedImage.getRunLengthCodeCount();
                m_iOriginalDemuxSize = getDemuxSize();

                updateImage();
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.toString());
        }
    }//GEN-LAST:event_m_guiOpenActionPerformed


    private void updateImage() {
        try {
            MdecDecoder_double oDecoder = new MdecDecoder_double(new StephensIDCT(), m_iWidth, m_iHeight);
            oDecoder.decode(m_oUncompressedImage.getStream());

            RgbIntImage oImg = new RgbIntImage(m_iWidth, m_iHeight);
            oDecoder.readDecodedRGB(oImg);
            BufferedImage bi = oImg.toBufferedImage();
            m_guiImage.setImage(bi);

            m_guiMdecCount.setText("MDEC count: " + m_oUncompressedImage.getRunLengthCodeCount() + " (" + m_iOriginalMdecCount + ")");

            int iDemuxSize = getDemuxSize();

            m_guiDemuxSize.setText("Demux size: " + iDemuxSize + " (" + m_iOriginalDemuxSize + ")");
        } catch (Throwable ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.toString());
        }

        updateBlocks();

    }

    private int getDemuxSize() throws IOException, UncompressionException {
        DemuxFrameUncompressor_FF7.Recompressor_FF7 oRecompressor =
                new DemuxFrameUncompressor_FF7.Recompressor_FF7();

        int iQscale = m_oUncompressedImage.getMacroBlock(0, 0).Y1.getQscale();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitStreamWriter oBitWriter = new BitStreamWriter(baos);
        oRecompressor.compressToDemuxFF7(oBitWriter, iQscale, m_oUncompressedImage.getRunLengthCodeCount());
        oRecompressor.write(m_oUncompressedImage.getStream());
        oBitWriter.close();
        return baos.size();
    }

    private void updateBlocks() {
        if (m_guiImage.getHighlightX() < 0) return;
        MdecInputStream oMBlk = m_oUncompressedImage.getStream(m_guiImage.getHighlightX(),
                                                               m_guiImage.getHighlightY());
        try {
            m_oMacBlockDecoder.decode(oMBlk);
        } catch (UncompressionException ex) {
        }
        //oMBlk.write(m_oMacBlockDecoder, 0);
        RgbIntImage oImg = new RgbIntImage(16, 16);
        //m_oMacBlockDecoder.readDecodedRGB(oImg);
        PsxYuvImage oYuv = new PsxYuvImage(16, 16);
        //m_oMacBlockDecoder.readDecodedPsxYuv(oYuv);
        m_guiCb.setScale(8);
        m_guiCb.setImage(oYuv.CbToBufferedImage());
        m_guiCr.setScale(8);
        m_guiCr.setImage(oYuv.CrToBufferedImage());

        BufferedImage bi = oYuv.YToBufferedImage();
        m_guiY1.setScale(8);
        m_guiY1.setImage(bi.getSubimage(0, 0, 8, 8));
        m_guiY2.setScale(8);
        m_guiY2.setImage(bi.getSubimage(8, 0, 8, 8));
        m_guiY3.setScale(8);
        m_guiY3.setImage(bi.getSubimage(0, 8, 8, 8));
        m_guiY4.setScale(8);
        m_guiY4.setImage(bi.getSubimage(8, 8, 8, 8));
    }




    private void m_guiY1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_guiY1MousePressed
        MacroBlock oMacBlk = m_oUncompressedImage.getMacroBlock(m_guiImage.getHighlightX(), m_guiImage.getHighlightY());
        scaleBlockQscale(oMacBlk.Y1);

    }//GEN-LAST:event_m_guiY1MousePressed

    private void m_guiY2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_guiY2MousePressed
        MacroBlock oMacBlk = m_oUncompressedImage.getMacroBlock(m_guiImage.getHighlightX(), m_guiImage.getHighlightY());
        scaleBlockQscale(oMacBlk.Y2);

    }//GEN-LAST:event_m_guiY2MousePressed

    private void m_guiCrMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_guiCrMousePressed
        MacroBlock oMacBlk = m_oUncompressedImage.getMacroBlock(m_guiImage.getHighlightX(), m_guiImage.getHighlightY());
        scaleBlockQscale(oMacBlk.Cr);

    }//GEN-LAST:event_m_guiCrMousePressed

    private void m_guiY3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_guiY3MousePressed
        MacroBlock oMacBlk = m_oUncompressedImage.getMacroBlock(m_guiImage.getHighlightX(), m_guiImage.getHighlightY());
        scaleBlockQscale(oMacBlk.Y3);

    }//GEN-LAST:event_m_guiY3MousePressed

    private void m_guiY4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_guiY4MousePressed
        MacroBlock oMacBlk = m_oUncompressedImage.getMacroBlock(m_guiImage.getHighlightX(), m_guiImage.getHighlightY());
        scaleBlockQscale(oMacBlk.Y4);

    }//GEN-LAST:event_m_guiY4MousePressed

    private void m_guiCbMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_guiCbMousePressed
        MacroBlock oMacBlk = m_oUncompressedImage.getMacroBlock(m_guiImage.getHighlightX(), m_guiImage.getHighlightY());
        scaleBlockQscale(oMacBlk.Cb);

    }//GEN-LAST:event_m_guiCbMousePressed

    private void m_guiImageMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_m_guiImageMousePressed
        m_guiImage.setHighlight(evt.getX(), evt.getY());
        updateBlocks();
        
    }//GEN-LAST:event_m_guiImageMousePressed

    private void scaleBlockQscale(Block oBlk) {
        int iQscale = oBlk.getQscale();
        int iNewQscale = ((Integer)m_guiQscaleSpin.getValue()).intValue();
        oBlk.changeQuantizationScale(iNewQscale);
        oBlk.changeQuantizationScale(iQscale);

        updateImage();
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DemuxQualityEditor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private jpsxdec.plugins.psx.video.encode.ImagePanel m_guiCb;
    private jpsxdec.plugins.psx.video.encode.ImagePanel m_guiCr;
    private javax.swing.JLabel m_guiDemuxSize;
    private jpsxdec.plugins.psx.video.encode.ImagePanel m_guiImage;
    private javax.swing.JLabel m_guiMdecCount;
    private javax.swing.JButton m_guiOpen;
    private javax.swing.JSpinner m_guiQscaleSpin;
    private jpsxdec.plugins.psx.video.encode.ImagePanel m_guiY1;
    private jpsxdec.plugins.psx.video.encode.ImagePanel m_guiY2;
    private jpsxdec.plugins.psx.video.encode.ImagePanel m_guiY3;
    private jpsxdec.plugins.psx.video.encode.ImagePanel m_guiY4;
    // End of variables declaration//GEN-END:variables

}
