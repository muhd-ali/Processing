interface Drawable {
  void draw();
}

// interface Drawable {
//   Drawer drawer;
//   ColorProvider colorProvider;
//   default void setDrawer(Drawer drawer) {
//     this.drawer = drawer;
//   }

//   default void setColorProvider(ColorProvider colorProvider) {
//     this.colorProvider = colorProvider;
//   }

//   default void draw() {
//     drawer.draw(this, colorProvider);
//   }
// }
