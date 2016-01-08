package breakpoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;

import ui.breakpoints.BreakpointUIComponent;

public class BreakpointFactory {
	private static HashMap<String, BreakpointDescriptor> mTypeMap;
	
	public static void initBreakpointFactory() {
		mTypeMap = new HashMap<>();
		
		register(WriteLpBreakpoint.class);
		register(WritePpBreakpoint.class);
		register(AllocateActiveBlockBreakpoint.class);
		register(EraseBlockBreakpoint.class);
		register(WriteAmplificationBreakpoint.class);
		register(WritesPerEraseBreakpoint.class);
		register(EraseCountBlockBreakpoint.class);
		register(EraseCountAnyBlockBreakpoint.class);
		register(CleanBlocksInPlane.class);
		register(CleanBlocksInChip.class);
		register(CleanBlocksInDevice.class);
	}
	
	public static IBreakpoint getBreakpoint(String type, Element breakpointElement) throws Exception {
		BreakpointBase breakpoint = (BreakpointBase) mTypeMap.get(type).getBreakpointClass().newInstance();
		breakpoint.readXml(breakpointElement);
		return breakpoint;
	}
	
	public static List<Class<? extends IBreakpoint>> getBreakpointClasses() {
		List<Class<? extends IBreakpoint>> result = new ArrayList<>();
		for (BreakpointDescriptor descriptor : mTypeMap.values()) {
			result.add(descriptor.getBreakpointClass());
		}
		
		return result;
	}
	
	public static List<BreakpointUIComponent> getBreakpointUIComponents(Class<? extends IBreakpoint> bpClass) {
		return mTypeMap.get(bpClass.getSimpleName()).getUIComponents();
	}
	
	private static void register(Class<? extends IBreakpoint> bpClass) {
		mTypeMap.put(bpClass.getSimpleName(), new BreakpointDescriptor(bpClass));
	}
}
