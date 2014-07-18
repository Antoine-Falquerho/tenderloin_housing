package com.tenderloinhousing.apps.activity;

import com.parse.ui.ParseLoginDispatchActivity;

public class CreateCaseDispatchActivity extends ParseLoginDispatchActivity {

  @Override
  protected Class<?> getTargetClass() {
    return CaseActivity.class;
  }
}
