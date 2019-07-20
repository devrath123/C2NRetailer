package com.example.c2n.offers_card.presenter.adapter;

/**
 * Created by shivani.singh on 29-06-2018.
 */

/*
public class OffersListAdapter extends RecyclerView.Adapter<OffersListAdapter.MyViewHolder> {


    List<OfferListDataModel> offerListDataModels;
    Context context;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private final OffersListAdapter.OfferCardSelected offerCardInterface;


    @Inject
    public OffersListAdapter(List<OfferListDataModel> offerListDataModels, Context context, OffersListAdapter.OfferCardSelected offerCardInterface) {
        this.offerListDataModels = offerListDataModels;
        this.context = context;
        this.offerCardInterface = offerCardInterface;
        Collections.sort(this.offerListDataModels);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_offers, parent, false);

        return new OffersListAdapter.MyViewHolder(view, offerCardInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OfferListDataModel offerListDataModel = offerListDataModels.get(position);
        holder.discountPrice.setText(offerListDataModel.getOfferDiscount());
        holder.offerDate.setText(format.format(offerListDataModel.getOfferStartdate()) + " to " + format.format(offerListDataModel.getOfferEndDate()));

        holder.image_pop_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context,holder.image_pop_up);
                popupMenu.inflate(R.menu.edit_offer);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.item_edit_offer:
                                break;

                        }
                        return false;
                    }
                });

                popupMenu.show();

            }
        });

        Boolean[] days = new Boolean[]{offerListDataModel.isSunday(), offerListDataModel.isMonday(), offerListDataModel.isTuesday(), offerListDataModel.isWednesday(), offerListDataModel.isThursday(), offerListDataModel.isFriday(), offerListDataModel.isSaturday()};
        String offerDays = "";
        for (int i = 0; i <= 6; i++) {
            if (days[i] == true) {
                offerDays = offerDays + setOfferDay(i, holder);
            }
        }
        holder.offerDay.setText(offerDays.substring(0, offerDays.length() - 2));
//        holder.offerDay.setText(offerDays);

    }

    public String setOfferDay(int i, MyViewHolder holder) {
        switch (i) {
            case 0:
                return "Sun, ";

            case 1:
                return "Mon, ";

            case 2:
                return "Tue, ";

            case 3:
                return "Wed, ";

            case 4:
                return "Thu, ";

            case 5:
                return "Fri, ";

            case 6:
                return "Sat, ";
            default:
                return null;
        }
    }


    @Override
    public int getItemCount() {
        return offerListDataModels.size();
    }

//    @Override
//    public void onClick(View v) {
//        int pos =  getAdapterPosition();
//        Log.d("offer_selcted-", offerListDataModels.get(pos).toString());
//        offerCardInterface.offerCardSelected(offerListDataModels.get(pos));
//    }


    public class MyViewHolder extends RecyclerView.MyViewHolder implements View.OnClickListener {

        private final OffersListAdapter.OfferCardSelected offerCardInterface;
        @BindView(R.id.text_discount_price)
        TextView discountPrice;

        @BindView(R.id.test_date)
        TextView offerDate;


        @BindView(R.id.test_day)
        TextView offerDay;

//        @BindView(R.id.image_pop)
//        ImageView image_pop_up;

        public MyViewHolder(View itemView, OffersListAdapter.OfferCardSelected offerCardInterface) {
            super(itemView);
            this.offerCardInterface = offerCardInterface;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Log.d("offer_selcted-", offerListDataModels.get(pos).toString());
            offerCardInterface.offerCardSelected(offerListDataModels.get(pos));
        }
    }

    public interface OfferCardSelected {
        void offerCardSelected(OfferListDataModel offerListDataModel);
    }
}
*/
