package info.androidhive.cardview;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    ImageView topback;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.imageslider).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        topback= (ImageView) findViewById(R.id.backdrop);
        topback.setBackgroundResource(R.drawable.imageslider);
        animationDrawable=(AnimationDrawable) topback.getBackground();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animationDrawable.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        animationDrawable.stop();
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] covers = new int[]{

                R.drawable.property,
                R.drawable.recruitment,
                R.drawable.business,
                R.drawable.health,
                R.drawable.vehicles,
                R.drawable.namechange,
                R.drawable.lostfound,
                R.drawable.announcement,
                R.drawable.travel,
                R.drawable.torent,
                R.drawable.publicnotice,
                R.drawable.swanted,
                R.drawable.services,
                R.drawable.retail,
                R.drawable.wedding,
                R.drawable.obituary,
                R.drawable.computers,
                R.drawable.marriage,
                R.drawable.astrology,
                R.drawable.entertainment,
                R.drawable.education};


       Album a = new Album("Property", covers[0]);
        albumList.add(a);

        a = new Album("Recruitment", covers[1]);
        albumList.add(a);

        a = new Album("Business", covers[2]);
        albumList.add(a);

        a = new Album("Health & Beauty", covers[3]);
        albumList.add(a);

        a = new Album("Vehicles", covers[4]);
        albumList.add(a);

        a = new Album("Change Of Address", covers[5]);
        albumList.add(a);

        a = new Album("Lost & Found", covers[6]);
        albumList.add(a);

        a = new Album("Announcement", covers[7]);
        albumList.add(a);

        a = new Album("Travel", covers[8]);
        albumList.add(a);
        a = new Album("To Rent", covers[9]);
        albumList.add(a);
        a = new Album("Public Notice", covers[10]);
        albumList.add(a);
        a = new Album("Situation Wanted", covers[11]);
        albumList.add(a);
        a = new Album("Services", covers[12]);
        albumList.add(a);
        a = new Album("Retail", covers[13]);
        albumList.add(a);
        a = new Album("Wedding Arrangements", covers[14]);
        albumList.add(a);
        a = new Album("Obituary", covers[15]);
        albumList.add(a);
        a = new Album("Computers", covers[16]);
        albumList.add(a);
        a = new Album("Marriage Bureau", covers[17]);
        albumList.add(a);
        a = new Album("Astrology", covers[18]);
        albumList.add(a);
        a = new Album("Entertainment", covers[19]);
        albumList.add(a);
        a = new Album("Education", covers[20]);
        albumList.add(a);



        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
