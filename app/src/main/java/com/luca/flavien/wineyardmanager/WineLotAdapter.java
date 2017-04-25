package com.luca.flavien.wineyardmanager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.luca.flavien.wineyardmanager.DB.Object.Job;
import com.luca.flavien.wineyardmanager.DB.Object.WineLot;

/**
 * Created by flavien on 25/04/17.
 */

public class WineLotAdapter extends ArrayAdapter<WineLot> {

    public WineLotAdapter(Context context, List<WineLot> wineLot) {
        super(context, 0, wineLot);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_location, parent, false);
        }

        WineLotViewHolder wineLotViewHolder= (WineLotViewHolder) convertView.getTag();
        if(wineLotViewHolder == null){
            wineLotViewHolder = new WineLotViewHolder();
            wineLotViewHolder.winelotName = (TextView) convertView.findViewById(R.id.location_name);
            //viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(wineLotViewHolder);
        }

        WineLot winelot = getItem(position);

        wineLotViewHolder.winelotName.setText(winelot.getName());


            /*String pathImg = vin.getImg();
            if(pathImg== null || pathImg==""){
                viewHolder.avatar.setImageResource(R.drawable.wine_default);
            }else{
                Bitmap bMap = BitmapFactory.decodeFile(vin.getImg());
                viewHolder.avatar.setImageBitmap(bMap);
            }*/


        return convertView;
    }


    private class WineLotViewHolder{
        public TextView winelotName;
    }

}
