package com.example.notesdemo;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.notesdemo.databinding.ActivityAddNotePageBindingImpl;
import com.example.notesdemo.databinding.ActivityNotesHomeBindingImpl;
import com.example.notesdemo.databinding.CardviewRecyclerviewNotesBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYADDNOTEPAGE = 1;

  private static final int LAYOUT_ACTIVITYNOTESHOME = 2;

  private static final int LAYOUT_CARDVIEWRECYCLERVIEWNOTES = 3;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(3);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.notesdemo.R.layout.activity_add_note_page, LAYOUT_ACTIVITYADDNOTEPAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.notesdemo.R.layout.activity_notes_home, LAYOUT_ACTIVITYNOTESHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.notesdemo.R.layout.cardview_recyclerview_notes, LAYOUT_CARDVIEWRECYCLERVIEWNOTES);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYADDNOTEPAGE: {
          if ("layout/activity_add_note_page_0".equals(tag)) {
            return new ActivityAddNotePageBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_add_note_page is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYNOTESHOME: {
          if ("layout/activity_notes_home_0".equals(tag)) {
            return new ActivityNotesHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_notes_home is invalid. Received: " + tag);
        }
        case  LAYOUT_CARDVIEWRECYCLERVIEWNOTES: {
          if ("layout/cardview_recyclerview_notes_0".equals(tag)) {
            return new CardviewRecyclerviewNotesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for cardview_recyclerview_notes is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(3);

    static {
      sKeys.put("layout/activity_add_note_page_0", com.example.notesdemo.R.layout.activity_add_note_page);
      sKeys.put("layout/activity_notes_home_0", com.example.notesdemo.R.layout.activity_notes_home);
      sKeys.put("layout/cardview_recyclerview_notes_0", com.example.notesdemo.R.layout.cardview_recyclerview_notes);
    }
  }
}
