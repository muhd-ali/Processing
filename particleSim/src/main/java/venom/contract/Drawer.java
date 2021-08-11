package venom.contract;

public interface Drawer<T> {
	void draw(T obj, ColorProvider<T> colorProvider);
}
