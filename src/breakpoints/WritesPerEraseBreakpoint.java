package breakpoints;

import entities.Device;
import entities.StatisticsGetter;
import manager.LogicalWritesPerEraseGetter;
import manager.SSDManager;

public class WritesPerEraseBreakpoint extends BreakpointBase {
	private double mValue;
	
	@Override
	public boolean breakpointHit(Device<?, ?, ?, ?> previousDevice,
			Device<?, ?, ?, ?> currentDevice) {
		for(StatisticsGetter getter : SSDManager.getCurrentManager().getStatisticsGetters()){
			if(LogicalWritesPerEraseGetter.class.isInstance(getter)){
				double oldValue = previousDevice == null ? Double.MIN_VALUE : getter.getStatistics(previousDevice).get(0).getValue();
				double currentValue = getter.getStatistics(currentDevice).get(0).getValue();
				mIsHit = oldValue < mValue && currentValue >= mValue;
				return mIsHit;
			}
		}
		
		mIsHit = false;
		return false;
	}

	public double getValue() {
		return mValue;
	}

	public void setValue(double value) {
		mValue = value;
	}
	
	@Override
	public String getDescription() {
		return "Writes per erase reach " + mValue;
	}

	@Override
	public String getDisplayName() {
		return "Writes per erase reach W";
	}

	@Override
	public void addComponents() {
		mComponents.add(new BreakpointComponent("value", double.class, "Writes per erase"));
	}

}
