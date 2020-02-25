package com.chikeandroid.tutsplus_glide;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created gennadij schamanskij on 24/02/2020.
 * e-mail:superslon74@gmail.com
 */

public class MainActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    private ArrayList<ModelViewPhoto> mSpacePhotos2= new ArrayList<ModelViewPhoto>();

    public class OkHttpHandler extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            Request.Builder builder = new Request.Builder();
            builder.url("https://api.unsplash.com/photos/?client_id=cf49c08b444ff4cb9e4d126b7e9f7513ba1ee58de7906e4360afc1a33d1bf4c0");
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONArray jsonRoot =  null;
            JSONObject jsonObject = null;
            JSONObject jsonObject2 = null;
            JSONObject jsonObject3 = null;

            String str1 =null;
            String str2 =null;
            String str3 =null;
            String str4 =null;


            try {
                jsonRoot = new JSONArray(s);
                for(int i=0;i<9;i++) {

                    jsonObject = jsonRoot.getJSONObject(i);

                    str1 = jsonObject.getString("user");

                    jsonObject2 = new JSONObject(str1);
                    str2 = jsonObject2.getString("name");

                    str3 = jsonObject2.getString("profile_image");

                    jsonObject3 = new JSONObject(str3);
                    str4 = jsonObject3.getString("large");

                    mSpacePhotos2.add(new ModelViewPhoto(str4, str2));
                }
                MainActivity.ImageGalleryAdapter adapter = new MainActivity.ImageGalleryAdapter(getApplicationContext(),mSpacePhotos2);
                recyclerView.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_gallery);

          layoutManager = new GridLayoutManager(this, 2);
        recyclerView = (RecyclerView) findViewById(R.id.rv_images);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute();

    }

    private class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>  {

        @Override
        public ImageGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View photoView = inflater.inflate(R.layout.item_photo, parent, false);

            ImageGalleryAdapter.MyViewHolder viewHolder = new ImageGalleryAdapter.MyViewHolder(photoView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ImageGalleryAdapter.MyViewHolder holder, int position) {

            ModelViewPhoto spacePhoto = mSpacePhotos.get(position);
            ImageView imageView = holder.mPhotoImageView;
            TextView textView = holder.textView;

            textView.setText(spacePhoto.getTitle());

            Glide.with(mContext)
                    .load(spacePhoto.getUrl())
                    .placeholder(R.drawable.ic_cloud_off_red)
                    .into(imageView)
            ;
        }

        @Override
        public int getItemCount() {
            return   mSpacePhotos.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView mPhotoImageView;
            public TextView textView;
            public MyViewHolder(View itemView) {

                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.iv_text);
                mPhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo);   //  iv_photo
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    ModelViewPhoto spacePhoto = mSpacePhotos.get(position);
                    Intent intent = new Intent(mContext, SpacePhotoActivity.class);
                    intent.putExtra(SpacePhotoActivity.EXTRA_SPACE_PHOTO, spacePhoto);
                    startActivity(intent);
                }
            }
        }
        private ArrayList<ModelViewPhoto> mSpacePhotos = new ArrayList<ModelViewPhoto>();
        private Context mContext;
        public ImageGalleryAdapter(Context context, ArrayList<ModelViewPhoto> mSpacePhotos){
            mContext = context;
            this.mSpacePhotos = mSpacePhotos;
        }
    }
}