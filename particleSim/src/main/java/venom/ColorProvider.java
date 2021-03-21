package venom;

interface ColorProvider<T> {
	float col1();

	float col2();

	float col3();

	float col4();

	void setDataObject(T obj);
}