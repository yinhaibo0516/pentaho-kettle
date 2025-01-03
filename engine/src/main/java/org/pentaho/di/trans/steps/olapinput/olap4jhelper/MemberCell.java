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


package org.pentaho.di.trans.steps.olapinput.olap4jhelper;

import java.util.List;

public class MemberCell extends AbstractBaseCell {

  private String parentDimension = null;

  private String parentMember = null;

  private MemberCell rightOf = null;

  private String uniqueName;

  private String rightOfDimension;

  private List<String> memberPath;

  /**
   *
   * Blank Constructor for Serializable niceness, don't use it.
   *
   */
  public MemberCell() {
    super();
  }

  /**
   *
   * Creates a member cell.
   *
   * @param b
   * @param c
   */
  public MemberCell( final boolean right, final boolean sameAsPrev ) {
    super();
    this.right = right;
    this.sameAsPrev = sameAsPrev;
  }

  public void setParentDimension( final String parDim ) {
    parentDimension = parDim;
  }

  public String getParentDimension() {
    return parentDimension;
  }

  /**
   * TODO JAVADOC
   *
   * @param parentMember
   */
  public void setParentMember( final String parentMember ) {

    this.parentMember = parentMember;

  }

  public String getParentMember() {
    return parentMember;
  }

  /**
   * TODO JAVADOC
   *
   * @param memberCell
   */
  public void setRightOf( final MemberCell memberCell ) {
    this.rightOf = memberCell;

  }

  public MemberCell getRightOf() {
    return rightOf;
  }

  /**
   * TODO JAVADOC
   *
   * @param name
   */
  public void setRightOfDimension( String name ) {

    this.rightOfDimension = name;

  }

  public String getRightOfDimension() {
    return this.rightOfDimension;
  }

  public void setMemberPath( List<String> memberPath ) {
    this.memberPath = memberPath;

  }

  public List<String> getMemberPath() {
    return memberPath;
  }

  public String getUniqueName() {
    return uniqueName;
  }

  public void setUniqueName( String name ) {
    uniqueName = name;
  }

}
