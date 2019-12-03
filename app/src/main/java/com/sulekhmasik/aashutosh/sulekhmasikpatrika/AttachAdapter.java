package com.sulekhmasik.aashutosh.sulekhmasikpatrika;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

class AttachAdapter extends
        RecyclerView.Adapter<AttachAdapter.AddViewHolder> {
    private ArrayList<CardData> data;
    public Context parContext;
    private boolean homeFlag;

    public static class AddViewHolder extends
            RecyclerView.ViewHolder{
        ImageView cardImageView;
        TextView cardTitleView;
        TextView cardExcView;

        public AddViewHolder(View itemView){
            super(itemView);
            this.cardImageView = (ImageView) itemView.findViewById(R.id.cardImage);
            this.cardTitleView = (TextView) itemView.findViewById(R.id.cardTitle);
            this.cardExcView = (TextView) itemView.findViewById(R.id.cardExcerpt);
        }
    }
    public AttachAdapter(ArrayList<CardData> d,Context c, Boolean flag) {
        this.data = d;
        this.parContext = c;
        this.homeFlag = flag;
    }

    public void clear(){
        final int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0,size);
    }

    @Override
    public AddViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.articlecard,parent,false);
        //view.setOnClickListener(articleDisplay.clickListener);
        return (new AddViewHolder(view));
    }

    @Override
    public void onBindViewHolder(final AddViewHolder holder, final int listPosition){
        TextView titleView = holder.cardTitleView;
        TextView excView = holder.cardExcView;
        ImageView imgView = holder.cardImageView;

        View view = holder.itemView;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(parContext,articlePage.class);
                in.putExtra("data",data.get(listPosition));
                parContext.startActivity(in);
            }
        });

        titleView.setText(data.get(listPosition).getTitle());
        excView.setText(data.get(listPosition).getExcerpt());

        int img_id = data.get(listPosition).getImgId();
        String img_url = data.get(listPosition).getImg();
        File folder = new File(parContext.getFilesDir(), "media/thumbnails/");
        final File file = new File(folder, img_id + ".jpg");
        if (file.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(file.toString());
            imgView.setImageBitmap(bmp);
        } else {
            Picasso.get().load(img_url)
                    .into(new PhotoLoader(parContext.getFilesDir().getPath(),img_id+".jpg",null));
            Picasso.get().load(new File(parContext.getFilesDir().getPath()+"/media/"+img_id+".jpg"))
                    .resize(400, 300).centerCrop()
                    .into(new PhotoLoader(parContext.getFilesDir().getPath(), "thumbnails/" + img_id + ".jpg",imgView));
        }
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

}
