package com.yangsheng.ydzd_lb.androidpnpro;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by yangsheng on 2016/6/18.
 */
public class MyDateSelector {
    interface IDateSelectorListener {
        public void onEnter(String time);

        public void onCancle();
    }

    private IDateSelectorListener mListener;
    private Context mContext;
    private Activity mActivity;
    private PopupWindow popupWindow;

    private ListView lv_hour, lv_time, lv_year, lv_month, lv_day;
    private MyTimeAdapter day_adapter;
    private TextView txt_choose_time, txt_choose_date;
    private LinearLayout lin_time, lin_date;
    private TextView txt_cancle, txt_enter;

    private int mFlag = 0;  //0表示选择时间  1表示选择日期
    private String[] hours, minutes, years, months, days;
    private int day_count;
    private int choose_year = -1, choose_month = -1, choose_day = -1, choose_hour = -1, choose_minute = -1;  //当前选择的日期

    private int mAnimatorStyle = -1;  //popupWindow动画风格 可自定义
    private int selectedItemColor = -1;   //选中条的颜色

    public MyDateSelector(Activity activity) {
        this.mActivity = activity;
        this.mContext = activity;

    }

    public void setOnDateSelectorListener(IDateSelectorListener listener) {
        this.mListener = listener;
    }

    public void initPopWindow() {

        View popView = View.inflate(mContext, R.layout.pop_view, null);
        lv_hour = (ListView) popView.findViewById(R.id.lv_hour);
        lv_time = (ListView) popView.findViewById(R.id.lv_time);
        lv_year = (ListView) popView.findViewById(R.id.lv_year);
        lv_month = (ListView) popView.findViewById(R.id.lv_month);
        lv_day = (ListView) popView.findViewById(R.id.lv_day);
        lin_date = (LinearLayout) popView.findViewById(R.id.lin_date);
        lin_time = (LinearLayout) popView.findViewById(R.id.lin_time);
        lin_date.setVisibility(View.GONE);
        lin_time.setVisibility(View.VISIBLE);
        mFlag = 0;
        txt_choose_time = (TextView) popView.findViewById(R.id.txt_choose_time);
        txt_choose_date = (TextView) popView.findViewById(R.id.txt_choose_date);
        txt_choose_time.setOnClickListener(new MyListener());
        txt_choose_date.setOnClickListener(new MyListener());
        txt_enter = (TextView) popView.findViewById(R.id.txt_enter);
        txt_cancle = (TextView) popView.findViewById(R.id.txt_cancle);
        txt_enter.setOnClickListener(new MyListener());
        txt_cancle.setOnClickListener(new MyListener());

        initBaseData();

        lv_year.setAdapter(new MyTimeAdapter(years));
        lv_year.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    System.out.println("----滑动停止了");
                    int top = (int) view.getChildAt(0).getY();
                    System.out.println("-------pos :0" + "  top :" + top);
                    if (Math.abs(top) > dip2px(mContext, 50) / 2) {
                        view.setSelection(view.getFirstVisiblePosition() + 1);
//                        view.scrollBy(0,dip2px(mContext,50)+top);

                    } else {
                        view.setSelection(view.getFirstVisiblePosition());
//                        view.scrollBy(0, top);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        lv_month.setAdapter(new MyTimeAdapter(months));
        lv_month.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    System.out.println("----滑动停止了");
                    int top = (int) view.getChildAt(0).getY();
                    System.out.println("-------pos :0" + "  top :" + top);
                    if (Math.abs(top) > dip2px(mContext, 50) / 2) {
                        view.setSelection(view.getFirstVisiblePosition() + 1);
//                        view.scrollBy(0,dip2px(mContext,50)+top);

                    } else {
                        view.setSelection(view.getFirstVisiblePosition());
//                        view.scrollBy(0, top);
                    }

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        lv_day.setAdapter(day_adapter = new MyTimeAdapter(days, day_count));
        lv_day.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    System.out.println("----滑动停止了");
                    int top = (int) view.getChildAt(0).getY();
                    System.out.println("-------pos :0" + "  top :" + top);
                    if (Math.abs(top) > dip2px(mContext, 50) / 2) {
                        view.setSelection(view.getFirstVisiblePosition() + 1);
//                        view.scrollBy(0,dip2px(mContext,50)+top);

                    } else {
                        view.setSelection(view.getFirstVisiblePosition());
//                        view.scrollBy(0, top);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        lv_hour.setAdapter(new MyTimeAdapter(hours));
        lv_hour.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    System.out.println("----滑动停止了");
                    int top = (int) view.getChildAt(0).getY();
                    System.out.println("-------pos :0" + "  top :" + top);
                    if (Math.abs(top) > dip2px(mContext, 50) / 2) {
                        view.setSelection(view.getFirstVisiblePosition() + 1);
//                        view.scrollBy(0,dip2px(mContext,50)+top);

                    } else {
                        view.setSelection(view.getFirstVisiblePosition());
//                        view.scrollBy(0, top);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        lv_time.setAdapter(new MyTimeAdapter(minutes));
        lv_time.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    System.out.println("----滑动停止了");
                    int top = (int) view.getChildAt(0).getY();
                    System.out.println("-------pos :0" + "  top :" + top);
                    if (Math.abs(top) > dip2px(mContext, 50) / 2) {
                        view.setSelection(view.getFirstVisiblePosition() + 1);
//                        view.scrollBy(0,dip2px(mContext,50)+top);

                    } else {
                        view.setSelection(view.getFirstVisiblePosition());
//                        view.scrollBy(0, top);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        popupWindow = new PopupWindow(popView, -1, dip2px(mContext, 250));
//        popupWindow.
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.getBackground().setAlpha(100);
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        if (mAnimatorStyle == -1) {
            popupWindow.setAnimationStyle(R.style.pop_anim_style);
        } else {
            popupWindow.setAnimationStyle(mAnimatorStyle);
        }

    }

    public void show(View view, int gravity) {
        initTime();
        popupWindow.showAtLocation(view, gravity, 0, getNavigationBarHeight());

    }

    public void show(View view) {
        show(view, Gravity.BOTTOM);
    }

    /**
     * 初始化基础的数据
     */
    private void initBaseData() {
        hours = new String[]{"0时", "1时", "2时", "3时", "4时", "5时", "6时", "7时", "8时", "9时", "10时", "11时", "12时", "13时", "14时", "15时", "16时", "17时", "18时", "19时", "20时", "21时", "22时", "23时"};
        minutes = new String[]{"0分", "1分", "2分", "3分", "4分", "5分", "6分", "7分",
                "8分", "9分", "10分", "11分", "12分", "13分", "14分", "15分", "16分",
                "17分", "18分", "19分", "20分", "21分", "22分", "23分", "24分", "25分", "26分", "27分",
                "28分", "29分", "30分", "31分", "32分", "33分", "34分", "35分", "36分",
                "37分", "38分", "39分", "40分", "41分", "42分", "43分", "45分", "46分", "47分",
                "48分", "49分", "50分", "51分", "52分", "53分", "54分", "55分", "56分", "57分",
                "58分", "59分"};
        years = new String[8];
        Calendar calendar = Calendar.getInstance();
        int cur_year = calendar.get(Calendar.YEAR);

        for (int i = 0; i < 8; i++) {
            years[i] = (cur_year - 3 + i) + "";
        }
        months = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        days = new String[]{"1日", "2日", "3日", "4日", "5日", "6日", "7日",
                "8日", "9日", "10日", "11日", "12日", "13日", "14日", "15日", "16日",
                "17日", "18日", "19日", "20日", "21日", "22日", "23日", "24日", "25日", "26日", "27日",
                "28日", "29日", "30日", "31日"};
        day_count = 31; //用来控制每月的个数   日的adapter的count为这个
    }

    /**
     * 初始化时间选择器的时间
     */
    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        if (choose_year == -1) {
            choose_year = calendar.get(Calendar.YEAR);
        }
        if (choose_month == -1) {
            choose_month = calendar.get(Calendar.MONTH) + 1;
        }
        if (choose_day == -1) {
            choose_day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        if (choose_hour == -1) {
            choose_hour = calendar.get(Calendar.HOUR_OF_DAY);
        }
        if (choose_minute == -1) {
            choose_minute = calendar.get(Calendar.MINUTE);
        }
//        2015-(2016-3)-1
        lv_year.setSelection(choose_year - (calendar.get(Calendar.YEAR) - 3) - 1);
        lv_month.setSelection(choose_month - 2);
        lv_day.setSelection(choose_day - 2);
        lv_hour.setSelection(choose_hour - 1);
        lv_time.setSelection(choose_minute - 1);
        // ------2016年6月18日16时47分
//        System.out.println("------"+choose_year +"年"+choose_month+"月"+choose_day+"日"+choose_hour+"时"+choose_minute+"分");
    }

    private int getNavigationBarHeight() {
        Resources resources = mContext.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }

    class MyTimeAdapter extends BaseAdapter {

        private String[] mInfos;
        private int mConstant_count;

        public MyTimeAdapter(String[] infos) {
            this.mInfos = infos;
            this.mConstant_count = 0;
        }

        public MyTimeAdapter(String[] infos, int constant_count) {
            this.mInfos = infos;
            this.mConstant_count = constant_count;
        }

        @Override
        public int getCount() {
            if (mConstant_count != 0) {
                return mConstant_count;
            }
            return mInfos.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder holder = null;
            if (convertView != null) {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {
                view = View.inflate(mContext, R.layout.time_itme, null);
                holder = new ViewHolder();
                holder.txt_time = (TextView) view.findViewById(R.id.txt_time);
            }
            holder.txt_time.setText(mInfos[position]);
            view.setTag(holder);
            return view;
        }

        class ViewHolder {
            TextView txt_time;
        }
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txt_choose_time:
                    //// TODO: 2016/6/18  记录当前选择的时间
                    choose_year = Calendar.getInstance().get(Calendar.YEAR) - 3 + lv_year.getFirstVisiblePosition() + 1;
                    choose_month = lv_month.getFirstVisiblePosition() + 2;
                    choose_day = lv_day.getFirstVisiblePosition() + 2;
                    lin_date.setVisibility(View.GONE);
                    lin_time.setVisibility(View.VISIBLE);
                    txt_choose_date.setBackgroundColor(Color.parseColor("#ffffff"));
                    txt_choose_time.setBackgroundColor(Color.parseColor("#2D87E2"));
                    txt_choose_date.setTextColor(Color.parseColor("#757575"));
                    txt_choose_time.setTextColor(Color.parseColor("#ffffff"));
                    mFlag = 0;

                    break;
                case R.id.txt_choose_date:
                    choose_hour = lv_hour.getFirstVisiblePosition() + 1;
                    choose_minute = lv_time.getFirstVisiblePosition() + 1;
                    lin_date.setVisibility(View.VISIBLE);
                    lin_time.setVisibility(View.GONE);
                    txt_choose_time.setBackgroundColor(Color.parseColor("#ffffff"));
                    txt_choose_date.setBackgroundColor(Color.parseColor("#2D87E2"));
                    txt_choose_time.setTextColor(Color.parseColor("#757575"));
                    txt_choose_date.setTextColor(Color.parseColor("#ffffff"));
                    mFlag = 1;

                    break;

                case R.id.txt_cancle:
                    if (mListener != null) {
                        mListener.onCancle();
                    }
                    popupWindow.dismiss();

                    break;

                case R.id.txt_enter:
                    if (mFlag == 0) {
                        choose_hour = lv_hour.getFirstVisiblePosition() + 1;
                        choose_minute = lv_time.getFirstVisiblePosition() + 1;
                    } else {
                        choose_year = Calendar.getInstance().get(Calendar.YEAR) - 3 + lv_year.getFirstVisiblePosition() + 1;
                        choose_month = lv_month.getFirstVisiblePosition() + 2;
                        choose_day = lv_day.getFirstVisiblePosition() + 2;
                    }
                    if (mListener != null) {
                        mListener.onEnter(choose_year + "," + choose_month + "," + choose_day + "," + choose_hour + "," + choose_minute);
                    }
                    popupWindow.dismiss();
//                    System.out.println("------"+choose_year +"年"+choose_month+"月"+choose_day+"日"+choose_hour+"时"+choose_minute+"分");
                    break;
            }
        }
    }

//
//    public int obtainDayCountOfMonth(int month,int year){
//        int count = 31;
//        GregorianCalendar gregorianCalendar = new GregorianCalendar();
//        switch (month){
//            case 2:
//                if(gregorianCalendar.isLeapYear(year)){
//                   count = 28;
//                }else{
//                    count = 29;
//                }
//                break;
//            case 1:
//            case 3:
//            case 5:
//            case 7:
//            case 8:
//            case 10:
//            case 12:
//                count = 31;
//                break;
//            default:
//                count = 30;
//                break;
//        }
//        return count;
//    }


    public void setmAnimatorStyle(int mAnimatorStyle) {
        this.mAnimatorStyle = mAnimatorStyle;
    }

    /**
     * 只可传入 今年的左右4年
     *
     * @param choose_year
     */
    public void setChoose_year(int choose_year) {
        this.choose_year = choose_year;
    }

    public void setChoose_month(int choose_month) {
        this.choose_month = choose_month;
    }

    public void setChoose_day(int choose_day) {
        this.choose_day = choose_day;
    }

    public void setChoose_hour(int choose_hour) {
        this.choose_hour = choose_hour;
    }

    public void setChoose_minute(int choose_minute) {
        this.choose_minute = choose_minute;
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
