/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.di.trans.steps.getxmldata;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.vfs2.FileObject;
import org.dom4j.Document;
import org.dom4j.Node;
import org.pentaho.di.core.fileinput.FileInputList;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

/**
 * @author Samatar
 * @since 21-06-2007
 */
public class GetXMLDataData extends BaseStepData implements StepDataInterface {
  public String thisline, nextline, lastline;
  public Object[] previousRow;
  public RowMetaInterface inputRowMeta;
  public RowMetaInterface outputRowMeta;
  public RowMetaInterface convertRowMeta;
  public int nr_repeats;

  public NumberFormat nf;
  public DecimalFormat df;
  public DecimalFormatSymbols dfs;
  public SimpleDateFormat daf;
  public DateFormatSymbols dafs;

  public int nrInputFields;
  public String PathValue;
  public String prunePath; // identical to meta.getPrunePath() with some conditions set at init(), null when no pruning
  public boolean stopPruning; // used for a trick to stop the reader in pruning mode
  public boolean errorInRowButContinue; // true when actual row has an error and error handling is active: means
                                        // continue (error handling in this step should be redesigned)
  public String tokenStart;
  public String tokenEnd;
  public int nodenr;
  public int nodesize;
  public List<Node> an;
  public Object[] readrow;
  public int totalpreviousfields;
  public Map<String, String> NAMESPACE = new HashMap<String, String>();
  public List<String> NSPath = new ArrayList<String>();

  public int nrReadRow;

  /**
   * The XML files to read
   */
  public FileInputList files;

  public FileObject file;
  public int filenr;

  public FileInputStream fr;
  public BufferedInputStream is;
  public Document document;
  public String itemElement;
  public int itemCount;
  public int itemPosition;
  public long rownr;
  public int indexOfXmlField;

  RowMetaInterface outputMeta;

  public String filename;
  public String shortFilename;
  public String path;
  public String extension;
  public boolean hidden;
  public Date lastModificationDateTime;
  public String uriName;
  public String rootUriName;
  public long size;

  /**
   *
   */
  public GetXMLDataData() {
    super();

    thisline = null;
    nextline = null;
    nf = NumberFormat.getInstance();
    df = (DecimalFormat) nf;
    dfs = new DecimalFormatSymbols();
    daf = new SimpleDateFormat();
    dafs = new DateFormatSymbols();

    nr_repeats = 0;
    previousRow = null;
    filenr = 0;

    fr = null;
    is = null;
    indexOfXmlField = -1;

    nrInputFields = -1;
    PathValue = null;
    tokenStart = "@_";
    tokenEnd = "-";
    nodenr = 0;
    nodesize = 0;
    an = null;
    readrow = null;
    totalpreviousfields = 0;
    prunePath = "";
    stopPruning = false;
    errorInRowButContinue = false;
    nrReadRow = 0;
  }
}
