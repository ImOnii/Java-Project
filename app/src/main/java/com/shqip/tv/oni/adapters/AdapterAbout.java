package com.shqip.tv.oni.adapters;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shqip.tv.R;
import com.shqip.tv.oni.fragments.FragmentAbout;

import java.util.List;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class AdapterAbout extends RecyclerView.Adapter<AdapterAbout.UserViewHolder> {

    private List<FragmentAbout.Data> dataList;
    private Context context;

    public AdapterAbout(List<FragmentAbout.Data> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lsv_item_about, null);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {

        FragmentAbout.Data data = dataList.get(position);

        holder.image.setImageResource(data.getImage());
        holder.title.setText(data.getTitle());
        holder.sub_title.setText(data.getSub_title());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 3){
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://onihd.com/copyright/")));
                }else if (position == 4) {
                    final String appName = context.getPackageName();
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
                    }
                } else if (position == 5) {
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    PackageManager pm = context.getPackageManager();
                    Intent tempIntent = new Intent(Intent.ACTION_SEND);
                    tempIntent.setType("*/*");
                    List<ResolveInfo> resInfo = pm.queryIntentActivities(tempIntent, 0);
                    for (int i = 0; i < resInfo.size(); i++) {
                        ResolveInfo ri = resInfo.get(i);
                        if (ri.activityInfo.packageName.contains("android.gm")) {
                            myIntent.setComponent(new ComponentName(ri.activityInfo.packageName, ri.activityInfo.name));
                            myIntent.setAction(Intent.ACTION_SEND);
                            myIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"developer@onihd.com"});
                            myIntent.setType("message/rfc822");
                            myIntent.putExtra(Intent.EXTRA_TEXT, "Pershkruani me detaje...");
                            myIntent.putExtra(Intent.EXTRA_SUBJECT, "Ploteso Titullin.");
                        }
                    }
                    context.startActivity(myIntent);
                } 
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView sub_title;
        RelativeLayout relativeLayout;

        public UserViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            sub_title = (TextView) itemView.findViewById(R.id.sub_title);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.lyt_parent);
        }

    }

}