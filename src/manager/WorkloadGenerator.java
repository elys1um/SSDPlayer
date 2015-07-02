/*******************************************************************************
 * SSDPlayer Visualization Platform (Version 1.0)
 * Authors: Roman Shor, Gala Yadgar, Eitan Yaakobi, Assaf Schuster
 * Copyright (c) 2015, Technion � Israel Institute of Technology
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that
 * the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS 
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 *******************************************************************************/
package manager;

import java.io.FileNotFoundException;

import entities.Block;
import entities.Chip;
import entities.Device;
import entities.Page;
import entities.Plane;


public abstract class WorkloadGenerator <P extends Page, B extends Block<P>, T extends Plane<P,B>, C extends Chip<P,B,T>, D extends Device<P,B,T,C>, S extends SSDManager<P, B, T, C, D>> 
	implements TraceParser<P,B,T,C,D,S> {
	private String name;
	private int traceLength;
	private S manager;
	private D device;
	protected int lpRange;
	private int lp;
	private int temp;
	
	protected abstract int getLP();

	public WorkloadGenerator(String name, S manager, int traceLength) {
		this.name = name;
		this.traceLength = traceLength; 
		this.manager = manager;
		lpRange = manager.getLpRange();
		device = manager.getEmptyDevice();
	}
	
	public String getName() {
		return name;
	}
	
	public int getTraceLength() {
		return traceLength;
	}

	@Override
	public void open(String fileName) throws FileNotFoundException {
	}

	@Override
	public void close() {
	}

	@Override
	public String getLastCommand() {
		return "write " + lp + ", temp="+temp;
	}

	@Override
	public D parseNextCommand() {
		if(device != null) {			
			lp = getLP();
			temp = getLPTemprature(lp);
			device =  manager.writeLP(device, lp, temp);
		}
		return device;
	}

	@Override
	public D getCurrentDevice() {
		return device;
	}

	protected int getLPTemprature(int lp) {
		return 1/*dummy*/;
	}
}
