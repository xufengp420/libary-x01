package project.ismine.xufeng.com.ximalayademo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.model.tag.Tag;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private SwipeMenuRecyclerView recycleView;
    private MasonryAdapter adapter;
    private List<Object> objects = new ArrayList<>();
    private MediaPlayer mp = new MediaPlayer();//新建一个的实例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycleView = (SwipeMenuRecyclerView) findViewById(R.id.recycleView);

        adapter = new MasonryAdapter();
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        CommonRequest.getCategories(null, new IDataCallBack<CategoryList>() {
            @Override
            public void onSuccess(CategoryList categoryList) {
                List<Category> categories = categoryList.getCategories();
                objects.clear();
                objects.addAll(categories);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {
                Log.d("TAG", i + "");
            }
        });

    }


    /**
     * 获取对应儿童节目下的便签
     *
     * @param type 0-专辑标签，1-声音标签
     */
    public void getChildTag(String type, final String categoryId) {
        Map<String, String> mapx = new HashMap<String, String>();
        mapx.put(DTransferConstants.CATEGORY_ID, categoryId);
        mapx.put(DTransferConstants.TYPE, type.equals("") ? "0" : type);
        CommonRequest.getTags(mapx, new IDataCallBack<TagList>() {
            @Override
            public void onSuccess(TagList tagList) {
                objects.clear();
                objects.addAll(tagList.getTagList());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    /**
     * 获取标签下的专辑
     *
     * @param tagName 分类名称
     * @param page    页码  默认为1
     * @param calc    最火（1），最新（2），经典或播放最多（3） 默认最火
     */
    public void getAlbumList(String tagName, String page, String calc, String categoryId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.CATEGORY_ID, "" + categoryId);
        map.put(DTransferConstants.TAG_NAME, tagName);
        map.put(DTransferConstants.PAGE, page.equals("") ? "1" : page);
        map.put(DTransferConstants.CALC_DIMENSION, calc.equals("") ? "1" : calc);
        CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
            @Override
            public void onSuccess(AlbumList albumList) {
                objects.clear();
                objects.addAll(albumList.getAlbums());
                adapter.notifyDataSetChanged();
//                Log.d("TAG", "//////////////////");
//                List albums = albumList.getAlbums();
//                for (int i = 0; i < albumList.getAlbums().size(); i++) {
//                    Album ta = albumList.getAlbums().get(i);
//                    Log.d("TAG", ta.getAlbumTitle() + "");
//                    if (ta.getAlbumTitle().equals("儿童故事")) {
//                        getTrack(ta.getId() + "", "", "");
//                    }
//                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    private String cid = "";

    /**
     * 获取音频
     *
     * @param albumId 专辑ID
     * @param sort    排序
     * @param page    页码
     */
    public void getTrack(String albumId, String sort, String page) {
        Map<String, String> maps = new HashMap<String, String>();
        maps.put(DTransferConstants.ALBUM_ID, albumId);
        maps.put(DTransferConstants.SORT, sort.equals("") ? "asc" : sort);
        maps.put(DTransferConstants.PAGE, page.equals("") ? "1" : page);
        CommonRequest.getTracks(maps, new IDataCallBack<TrackList>() {
            @Override
            public void onSuccess(TrackList trackList) {
                objects.clear();
                objects.addAll(trackList.getTracks());
                adapter.notifyDataSetChanged();
//                List<Track> tracks = trackList.getTracks();
//                Log.d("TAG", "//////////////////");
//                for (int i = 0; i < tracks.size(); i++) {
//                    Track ta = tracks.get(i);
//                    Log.d("TAG", ta.getPlayUrlAmr() + "");
//                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    public class MasonryAdapter extends SwipeMenuAdapter<MasonryAdapter.MasonryView> {

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_view, parent, false);
            return view;
        }

        @Override
        public MasonryView onCompatCreateViewHolder(View realContentView, int viewType) {
            return new MasonryView(realContentView);
        }

        @Override
        public void onBindViewHolder(final MasonryView masonryView, final int position) {
            final Object object = objects.get(position);
            if (object instanceof Category) {
                Category category = (Category) object;
                masonryView.textView.setText(category.getCategoryName());
            } else if (object instanceof Tag) {
                Tag tag = (Tag) object;
                masonryView.textView.setText(tag.getTagName());
            } else if (object instanceof Album) {
                Album album = (Album) object;
                masonryView.textView.setText(album.getAlbumTitle());
            } else if (object instanceof Track) {
                Track track = (Track) object;
                masonryView.textView.setText(track.getTrackTitle());
            }

            masonryView.reView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (object instanceof Category) {
                        Category category = (Category) object;
                        cid = category.getId() + "";
                        getChildTag("", category.getId() + "");
                    } else if (object instanceof Tag) {
                        Tag tag = (Tag) object;
                        getAlbumList(tag.getTagName(), "", "", cid);
                    } else if (object instanceof Album) {
                        Album album = (Album) object;
                        getTrack(album.getId() + "", "", "");
                    } else if (object instanceof Track) {
                        Track track = (Track) object;
                        if (mp.isPlaying()) {
                            mp.stop();
                            mp.reset();
                        }
                        try {
                            mp.setDataSource(track.getPlayUrl32());
                            mp.prepare();
                            mp.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return objects.size();
        }

        public class MasonryView extends RecyclerView.ViewHolder {
            private RelativeLayout reView;
            private TextView textView;


            public MasonryView(View itemView) {
                super(itemView);
                reView = (RelativeLayout) itemView.findViewById(R.id.itemView);
                textView = (TextView) itemView.findViewById(R.id.textView);
            }
        }

    }
}
