package com.dk.safehome1;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import com.dk.safehome1.db.RouterViewModel;
import com.dk.safehome1.entity.Router;
import java.util.List;
import java.util.regex.Pattern;

import static android.widget.CompoundButton.*;

/**
 * Created by dk on 6/6/18.
 */

public class RouterActivity extends AppCompatActivity {

    public static final String ROUTER_ACTIVITY_TAG = "999";

    public static final int ROUTER_ACTIVITY_REQUEST_CODE = 3;
    public static final String EXTRA_ROUTER_ID = "com.dk.safeHome1.ROUTER_id";
    private RouterViewModel mRouterViewModel;



    long enterId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(RouterActivity.this, SafeActivity.class);
//                startActivityForResult(intent, ROUTER_ACTIVITY_REQUEST_CODE);
//            }
//        });

        enterId = (long)getIntent().getSerializableExtra(EnterActivity.EXTRA_ENTER_ID);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_place);
        final RouterListAdapter adapter = new RouterListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRouterViewModel = ViewModelProviders.of(this).get(RouterViewModel.class);

        mRouterViewModel.findAllByEnterId(enterId).observe(this, new Observer<List<Router>>() {
            @Override
            public void onChanged(@Nullable final List<Router> routers) {
                // Update the cached copy of the places in the adapter.
                adapter.setRouters(routers);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == ROUTER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            Router router = new Router();
//            router.setEnterId(enterId);
//            router.setTitle(data.getStringExtra(SafeActivity.EXTRA_REPLY));
//
//            mRouterViewModel.insert(router);
//        } else {
//            Toast.makeText(
//                    getApplicationContext(),
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG).show();
//        }
//    }

//    public static Intent newIntent(Context packageContext, long placeId) {
//        Intent intent = new Intent(packageContext, RouterActivity.class);
//        intent.putExtra(EXTRA_ROUTER_ID, placeId);
//        return intent;
//    }


    public class RouterListAdapter extends RecyclerView.Adapter<RouterListAdapter.WordViewHolder> {

        class WordViewHolder extends RecyclerView.ViewHolder {
            private final TextView routerItemView1;
            private final TextView routerItemView2;
            private CheckBox routerItemCheckBox;
            private Router current;

            public void bind(Router router) {

                current = router;

                routerItemCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            current.setMain(true);
                            mRouterViewModel.update(current);

                        } else {

                            current.setMain(false);
                            mRouterViewModel.update(current);
                        }
                    }
                });

                //Initiating router title
                routerItemView1.setText(current.getTitle());
                routerItemView2.setText("" + current.getSignalStrength());
                routerItemCheckBox.setChecked(current.isMain());


            }

            private WordViewHolder(View itemView) {
                super(itemView);
                routerItemView1 = itemView.findViewById(R.id.text_router_title1);
                routerItemView2 = itemView.findViewById(R.id.text_router_title2);
                routerItemCheckBox = itemView.findViewById(R.id.checkbox_router1);



//                routerItemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        Toast.makeText(RouterActivity.this, current.getRouterId() + "", Toast.LENGTH_LONG).show();
//                        Intent intent = RouterActivity.newIntent(RouterActivity.this, current.getRouterId());
//                        startActivity(intent);
//                    }
//                });
            }
        }

        private final LayoutInflater mInflater;
        private List<Router> mRouters; // Cached copy

        RouterListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

        @Override
        public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.recyclerview_router_item, parent, false);
            return new WordViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(WordViewHolder holder, int position) {
            if (mRouters != null) {
                Router current = mRouters.get(position);
                holder.bind(current);

            } else {
                // Covers the case of data not being ready yet.
                holder.routerItemView1.setText("No router");
            }
        }

        void setRouters(List<Router> routers){

            mRouters = routers;
            notifyDataSetChanged();
        }

        // getItemCount() is called many times, and when it is first called,
        // mRouters has not been updated (means initially, it's null, and we can't return null).
        @Override
        public int getItemCount() {
            if (mRouters != null)
                return mRouters.size();
            else return 0;
        }
    }




}
