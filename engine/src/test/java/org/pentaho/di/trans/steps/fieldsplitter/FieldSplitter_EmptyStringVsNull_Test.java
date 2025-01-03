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


package org.pentaho.di.trans.steps.fieldsplitter;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaString;
import org.pentaho.di.junit.rules.RestorePDIEngineEnvironment;
import org.pentaho.di.trans.TransTestingUtil;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.steps.StepMockUtil;
import org.pentaho.di.trans.steps.mock.StepMockHelper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author Andrey Khayrutdinov
 */
public class FieldSplitter_EmptyStringVsNull_Test {
  private StepMockHelper<FieldSplitterMeta, StepDataInterface> helper;
  @ClassRule public static RestorePDIEngineEnvironment env = new RestorePDIEngineEnvironment();

  @BeforeClass
  public static void initKettle() throws Exception {
    KettleEnvironment.init();
  }

  @Before
  public void setUp() {
    helper = StepMockUtil.getStepMockHelper( FieldSplitterMeta.class, "FieldSplitter_EmptyStringVsNull_Test" );
  }

  @After
  public void cleanUp() {
    helper.cleanUp();
  }

  @Test
  public void emptyAndNullsAreNotDifferent() throws Exception {
    System.setProperty( Const.KETTLE_EMPTY_STRING_DIFFERS_FROM_NULL, "N" );
    List<Object[]> expected = Arrays.asList(
      new Object[] { "a", "", "a" },
      new Object[] { "b", null, "b" },
      new Object[] { null }
    );
    executeAndAssertResults( expected );
  }


  @Test
  public void emptyAndNullsAreDifferent() throws Exception {
    System.setProperty( Const.KETTLE_EMPTY_STRING_DIFFERS_FROM_NULL, "Y" );
    List<Object[]> expected = Arrays.asList(
      new Object[] { "a", "", "a" },
      new Object[] { "b", "", "b" },
      new Object[] { "", "", "" }
    );
    executeAndAssertResults( expected );
  }

  private void executeAndAssertResults( List<Object[]> expected ) throws Exception {
    FieldSplitterMeta meta = new FieldSplitterMeta();
    meta.allocate( 3 );
    meta.setFieldName( new String[] { "s1", "s2", "s3" } );
    meta.setFieldType( new int[] { ValueMetaInterface.TYPE_STRING, ValueMetaInterface.TYPE_STRING, ValueMetaInterface.TYPE_STRING } );
    meta.setSplitField( "string" );
    meta.setDelimiter( "," );

    FieldSplitterData data = new FieldSplitterData();

    FieldSplitter step = createAndInitStep( meta, data );

    RowMeta input = new RowMeta();
    input.addValueMeta( new ValueMetaString( "string" ) );
    step.setInputRowMeta( input );

    step = spy( step );
    doReturn( new String[] { "a, ,a" } )
      .doReturn( new String[] { "b,,b" } )
      .doReturn( new String[] { null } )
      .when( step ).getRow();

    List<Object[]> actual = TransTestingUtil.execute( step, meta, data, 3, false );
    TransTestingUtil.assertResult( expected, actual );
  }

  private FieldSplitter createAndInitStep( FieldSplitterMeta meta, FieldSplitterData data ) {
    when( helper.stepMeta.getStepMetaInterface() ).thenReturn( meta );

    FieldSplitter step = new FieldSplitter( helper.stepMeta, data, 0, helper.transMeta, helper.trans );
    step.init( meta, data );
    return step;
  }
}
