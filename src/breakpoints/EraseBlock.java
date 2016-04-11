package breakpoints;

import entities.Device;

public class EraseBlock extends BreakpointBase {
	private int mBlockIndex;
	
	public EraseBlock() {
		super();
	}
	
	@Override
	public boolean breakpointHit(Device<?, ?, ?, ?> previousDevice,
			Device<?, ?, ?, ?> currentDevice) {
		if(previousDevice == null){
			return false;
		}
		int previousBlockEraseCount = previousDevice.getBlockByIndex(mBlockIndex).getEraseCounter();
		int currentBlockEraseCount = currentDevice.getBlockByIndex(mBlockIndex).getEraseCounter();
		return previousBlockEraseCount < currentBlockEraseCount;
	}

	public int getBlockIndex() {
		return mBlockIndex;
	}

	public void setBlockIndex(int blockIndex) {
		mBlockIndex = blockIndex;
	}

	@Override
	public String getDisplayName() {
		return "Erase block B";
	}

	@Override
	public String getDescription() {
		return "Erase block " + mBlockIndex;
	}

	@Override
	public void addComponents() {
		mComponents.add(new BreakpointComponent("blockIndex", int.class, "Block"));
	}

	@Override
	public boolean isEquals(IBreakpoint other) {
		if (!(other instanceof EraseBlock)) return false; 
		EraseBlock otherCasted = (EraseBlock) other;
		
		return mBlockIndex == otherCasted.getBlockIndex();
	}
}
