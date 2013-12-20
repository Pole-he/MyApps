package com.nathan.myapps.widget;

import com.nathan.myapps.widget.ViewFlow.ViewSwitchListener;


// 定义一个接口，FlowIndicator负责显示一个视觉指示器的总数量和当前视图可见视图。
public interface FlowIndicator extends ViewSwitchListener {

    /*
     * 设置当前ViewFlow。这个方法被调用的ViewFlow当FlowIndicator附属于它。
     */
    public void setViewFlow(ViewFlow view);

    /**
     * 
     * 滚动位置已经被改变了。一个FlowIndicator可能实现这个方法,以反映当前的位置
     */
    public void onScrolled(int h, int v, int oldh, int oldv);
}
