package com.example.notesdemo.databinding;
import com.example.notesdemo.R;
import com.example.notesdemo.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityNotesHomeBindingImpl extends ActivityNotesHomeBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.floatingbutton_add_notes_home, 1);
        sViewsWithIds.put(R.id.const_toolbar, 2);
        sViewsWithIds.put(R.id.const_toolbar_without_searchview, 3);
        sViewsWithIds.put(R.id.textview_mynotes, 4);
        sViewsWithIds.put(R.id.img_info, 5);
        sViewsWithIds.put(R.id.img_search, 6);
        sViewsWithIds.put(R.id.const_searchview, 7);
        sViewsWithIds.put(R.id.img_search_glass, 8);
        sViewsWithIds.put(R.id.img_line, 9);
        sViewsWithIds.put(R.id.edit_searchview, 10);
        sViewsWithIds.put(R.id.img_close, 11);
        sViewsWithIds.put(R.id.empty_lottie_animation, 12);
        sViewsWithIds.put(R.id.swipeToRefresh, 13);
        sViewsWithIds.put(R.id.skeletonLayout, 14);
        sViewsWithIds.put(R.id.recyclerview_notes_home, 15);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityNotesHomeBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private ActivityNotesHomeBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[7]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[3]
            , (android.widget.EditText) bindings[10]
            , (com.airbnb.lottie.LottieAnimationView) bindings[12]
            , (androidx.cardview.widget.CardView) bindings[1]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[8]
            , (androidx.recyclerview.widget.RecyclerView) bindings[15]
            , (com.faltenreich.skeletonlayout.SkeletonLayout) bindings[14]
            , (androidx.swiperefreshlayout.widget.SwipeRefreshLayout) bindings[13]
            , (android.widget.TextView) bindings[4]
            );
        this.constNoteHomeMain.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}