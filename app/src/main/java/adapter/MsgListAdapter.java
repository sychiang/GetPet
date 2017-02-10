package adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import iii.org.tw.getpet.ActMsgBox;
import iii.org.tw.getpet.R;
import model.UserMsg;

/**
 * Created by user on 2017/2/5.
 */

public class MsgListAdapter extends RecyclerView.Adapter<MsgListAdapter.ViewHolder> {
    private List<UserMsg> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public MsgListAdapter(List<UserMsg> data) {
        mData = data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvSender, tvIfRead;
        public CardView cardView;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            tvTitle = (TextView) view.findViewById(R.id.msglist_tvTitle);
            tvSender = (TextView) view.findViewById(R.id.msglist_tvSender);
            tvIfRead = (TextView) view.findViewById(R.id.msglist_tvIfRead);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.msglist, parent, false);
        final ViewHolder vholder = new ViewHolder(view);

        vholder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LayoutInflater inflater = LayoutInflater.from(ActMsgBox.this);
                final View view = inflater.inflate(R.layout.msgdetail_alertdialog, null);
                TextView msg_subject = (TextView) view.findViewById(R.id.msg_subject);
                TextView msg_sender = (TextView) view.findViewById(R.id.msg_sender);
                TextView msg_content = (TextView) view.findViewById(R.id.msg_content);

                int position = vholder.getAdapterPosition();
                msg_subject.setText(mData.get(position).getMsgType());
                msg_sender.setText(mData.get(position).getMsgFrom_userName());
                msg_content.setText(mData.get(position).getMsgContent());

                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog.setView(view);
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.create().show();
            }
        });
        return vholder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserMsg message = mData.get(position);
        holder.tvTitle.setText(message.getMsgType());
        holder.tvSender.setText(message.getMsgFrom_userName());
        holder.tvIfRead.setText(message.getMsgRead());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




}
