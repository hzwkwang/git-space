package cn.yiqi.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends FragmentBaiduMtj {

	
	protected boolean isVisible;//Fragment当前状态是否可见 
	
	protected View mView;
	
	
	/**
	 * 如果返回值是true返回键事件在fragment执行
	 * false在activity执行
	 * @return
	 */
	public abstract boolean onBackPressed();
	
	private FrontFragmentInterface frontFragmentInterface; 
	
	/**
	 * 判断acitivity中当前是哪个fragment
	 * @author Spring
	 *
	 */
	public interface FrontFragmentInterface {
	    public abstract void setFrontFragment(BaseFragment selectedFragment);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(!(getActivity() instanceof FrontFragmentInterface)){  
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");  
        }else{  
            this.frontFragmentInterface = (FrontFragmentInterface)getActivity();  
        }  
	}
	
	@Override  
    public void onStart() {  
        super.onStart();  
        //告诉FragmentActivity，当前Fragment在栈顶  
        frontFragmentInterface.setFrontFragment(this);  
    }  
	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		if (mView == null) {
//			mView = onInflaterContentView(inflater, container, savedInstanceState);
//		}
//		return mView;
//	}
//	
//	/**
//	 * 加载界面
//	 * @param inflater
//	 * @param container
//	 * @param savedInstanceState
//	 * @return
//	 */
//	protected View onInflaterContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//		return super.onCreateView(inflater, container, savedInstanceState);
//	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);

		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}

	/**
	 * Fragment可见
	 */
	protected void onVisible() {
		onDataLoadTime();
	}

	
	
	
	
	/**
	 * Fragment不可见
	 */
	protected void onInvisible() {}

	/**
	 * 联网获取数据的时候
	 */
	protected void onDataLoadTime(){}
	
	
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		if (mView!=null) {
			((ViewGroup)mView.getParent()).removeView(mView);
		}
		super.onDestroyView();
	}
	
	/**
	 * 设置 uri 到activity
	 * @param url
	 */
	protected void setUrlToActivity(String url, String title, String desc, String sharImgUrl) {
		if (onFragmentUrlChange==null) {
			return;
		}
		
		onFragmentUrlChange.onUrlChange(url, title, desc, sharImgUrl);
	}
	
	private OnFragmentUrlChange onFragmentUrlChange;
	
	public void setOnFragmentUrlChange(OnFragmentUrlChange onFragmentUrlChange){
		this.onFragmentUrlChange=onFragmentUrlChange;
	}
}
