// Generated code from Butter Knife. Do not modify!
package com.example.guideme;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ActivityMap$$ViewInjector<T extends com.example.guideme.ActivityMap> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492902, "field 'starting'");
    target.starting = finder.castView(view, 2131492902, "field 'starting'");
    view = finder.findRequiredView(source, 2131493012, "field 'destination'");
    target.destination = finder.castView(view, 2131493012, "field 'destination'");
    view = finder.findRequiredView(source, 2131493013, "field 'send' and method 'sendRequest'");
    target.send = finder.castView(view, 2131493013, "field 'send'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.sendRequest();
        }
      });
  }

  @Override public void reset(T target) {
    target.starting = null;
    target.destination = null;
    target.send = null;
  }
}
