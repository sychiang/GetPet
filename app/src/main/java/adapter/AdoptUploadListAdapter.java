package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import common.CDictionary;
import iii.org.tw.getpet.ActAdoptEdit;
import iii.org.tw.getpet.ActAdoptUploadList;
import iii.org.tw.getpet.ActHomePage;
import iii.org.tw.getpet.R;
import model.AdoptPair;
import model.object_ConditionOfAdoptPet;
import model.object_OfPictureImgurSite;
import model.object_petDataForSelfDB;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by user on 2017/2/11.
 */

public class AdoptUploadListAdapter extends RecyclerView.Adapter<AdoptUploadListAdapter.ViewHolder> {
    //private List<object_petDataForSelfDB> mData;
    private List<AdoptPair> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public AdoptUploadListAdapter(List<AdoptPair> data) {
        this.mData = data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_upload_date, tv_upload_name,tv_upload_kind,tv_upload_type,tv_upload_ifadopted,tv_upload_getter;
        public CardView cardView;
        public ImageView iv_upload_image;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            tv_upload_date = (TextView) view.findViewById(R.id.tv_upload_date);
            tv_upload_name = (TextView) view.findViewById(R.id.tv_upload_name);
            tv_upload_kind = (TextView) view.findViewById(R.id.tv_upload_kind);
            tv_upload_type = (TextView) view.findViewById(R.id.tv_upload_type);
            tv_upload_getter = (TextView) view.findViewById(R.id.tv_upload_getter);
            tv_upload_ifadopted = (TextView) view.findViewById(R.id.tv_upload_ifadopted);
            iv_upload_image = (ImageView) view.findViewById(R.id.iv_upload_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.content_act_adopt_upload_list, parent, false);
        final ViewHolder vholder = new ViewHolder(view);

        vholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vholder.getAdapterPosition();

                Intent intent = new Intent(mContext, ActAdoptEdit.class);
                //這裡要加物件
                object_petDataForSelfDB obj = new object_petDataForSelfDB();
                obj.setAnimalID(mData.get(position).getAnimalID());
                obj.setAnimalType(mData.get(position).getAnimalType());
                obj.setAnimalKind(mData.get(position).getAnimalKind());
                obj.setAnimalName(mData.get(position).getAnimalName());
                obj.setAnimalAddress(mData.get(position).getAnimalAddress());
                obj.setAnimalDate(mData.get(position).getAnimalDate());
                obj.setAnimalGender(mData.get(position).getAnimalGender());
                obj.setAnimalAge(String.valueOf(mData.get(position).getAnimalAge()));
                obj.setAnimalColor(mData.get(position).getAnimalColor());
                obj.setAnimalChip(mData.get(position).getAnimalChip());
                obj.setAnimalBirth(mData.get(position).getAnimalBirth());
                obj.setAnimalHealthy(mData.get(position).getAnimalHealthy());
                obj.setAnimalDisease_Other(mData.get(position).getAnimalDisease_Other());
                obj.setAnimalOwner_userID(mData.get(position).getAnimalOwner_userID());
                obj.setAnimalReason(mData.get(position).getAnimalReason());
                obj.setAnimalGetter_userID(mData.get(position).getAnimalGetter_userID());
                obj.setAnimalAdopted(mData.get(position).getAnimalAdopted());
                obj.setAnimalAdoptedDate(mData.get(position).getAnimalAdoptedDate());
                obj.setAnimalNote(mData.get(position).getAnimalNote());

                //***
                obj.setAnimalData_Pic(new ArrayList<object_OfPictureImgurSite>());
                for (int i = 0; i < mData.get(position).getAnimalData_Pic().size() ; i++) {
                    if(mData.get(position).getAnimalData_Pic().get(i)!=null){
                        object_OfPictureImgurSite img = new object_OfPictureImgurSite(String.valueOf(mData.get(position).getAnimalData_Pic().get(i).getAnimalPicID()),String.valueOf(mData.get(position).getAnimalData_Pic().get(i).getAnimalPic_animalID()),mData.get(position).getAnimalData_Pic().get(i).getAnimalPicAddress());
                        obj.getAnimalData_Pic().add(img);
                    }
                }

                obj.setAnimalData_Condition(new ArrayList<object_ConditionOfAdoptPet>());
                if(true/*mData.get(0).getAnimalData_Condition().get(0)!=null*/){

                    if(mData.get(position).getAnimalData_Condition().size()>0){
                        object_ConditionOfAdoptPet conditionOfAdoptPet = new object_ConditionOfAdoptPet();
                        conditionOfAdoptPet.setConditionID(mData.get(position).getAnimalData_Condition().get(0).getConditionID());
                        conditionOfAdoptPet.setCondition_animalID(mData.get(position).getAnimalData_Condition().get(0).getCondition_animalID());
                        conditionOfAdoptPet.setConditionAge(mData.get(position).getAnimalData_Condition().get(0).getConditionAge());
                        conditionOfAdoptPet.setConditionEconomy(mData.get(position).getAnimalData_Condition().get(0).getConditionEconomy());
                        conditionOfAdoptPet.setConditionHome(mData.get(position).getAnimalData_Condition().get(0).getConditionHome());
                        conditionOfAdoptPet.setConditionFamily(mData.get(position).getAnimalData_Condition().get(0).getConditionFamily());
                        conditionOfAdoptPet.setConditionReply(mData.get(position).getAnimalData_Condition().get(0).getConditionReply());
                        conditionOfAdoptPet.setConditionPaper(mData.get(position).getAnimalData_Condition().get(0).getConditionPaper());
                        conditionOfAdoptPet.setConditionFee(mData.get(position).getAnimalData_Condition().get(0).getConditionFee());
                        conditionOfAdoptPet.setConditionOther(mData.get(position).getAnimalData_Condition().get(0).getConditionOther());
                        obj.getAnimalData_Condition().add(conditionOfAdoptPet);
                    }



                }


                intent.putExtra("object_ConditionOfAdoptPet_objA",obj);
                Bundle bundle = new Bundle();

                //intent.putExtra("object_ConditionOfAdoptPet_objA",mData.get(position));
                mContext.startActivity(intent);
            }
        });

        return vholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AdoptPair item = mData.get(position);
        holder.tv_upload_name.setText(item.getAnimalName());
        holder.tv_upload_date.setText(item.getAnimalDate());

        holder.tv_upload_kind.setText(item.getAnimalKind());
        holder.tv_upload_type.setText(item.getAnimalType());
        if(item.getAnimalAdopted()!= null){
            holder.tv_upload_ifadopted.setText(item.getAnimalAdopted());
        }
        if(item.getAnimalGetter_userID() != null){
            holder.tv_upload_getter.setText(item.getAnimalGetter_userID());
            holder.tv_upload_getter.setVisibility(View.VISIBLE);
        }

        if(item.getAnimalData_Pic().size()>0){
            String imgURL = item.getAnimalData_Pic().get(0).getAnimalPicAddress();
            if(imgURL != null){
                if(imgURL.toLowerCase().endsWith(".jpg") || imgURL.toLowerCase().endsWith(".png")){
                    Glide.with(mContext).load(imgURL).into(holder.iv_upload_image);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
