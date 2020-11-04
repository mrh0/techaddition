package com.mrh0.techaddition.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;

public class Util {
	public static void writePos(String name, CompoundNBT nbt, BlockPos pos) {
		nbt.putInt(name+"_x", pos.getX());
		nbt.putInt(name+"_y", pos.getY());
		nbt.putInt(name+"_z", pos.getZ());
	}
	
	public static BlockPos readPos(String name, CompoundNBT nbt) {
		return new BlockPos(nbt.getInt(name+"_x"), nbt.getInt(name+"_y"), nbt.getInt(name+"_z"));
	}
	
	public static int min(int...v) {
		int m = Integer.MAX_VALUE;
		for(int i : v)
			if(i < m)
				m = i;
		return m;
	}
	
	public static int max(int...v) {
		int m = Integer.MIN_VALUE;
		for(int i : v)
			if(i > m)
				m = i;
		return m;
	}
	
	public static float mid(float a, float b) {
		return (a+b) / 2;
	}
	
	public static Vector3f f(BlockPos pos) {
		return new Vector3f(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public static float dif(float a, float b) {
		return Math.abs(a-b);
	}
	
	public static int dif(int a, int b) {
		return Math.abs(a-b);
	}
}
