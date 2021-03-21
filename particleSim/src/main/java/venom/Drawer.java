package venom;

interface Drawer<T> {
	void draw(T obj, ColorProvider<T> colorProvider);
}
