package jiuri.com.dagger2demo.ui;

import java.util.List;

/**
 * Created by user103 on 2017/9/8.
 */

public class FaceBeanBean {

    /**
     * face : [{"position":{"top":116,"left":125,"right":377,"bottom":368}}]
     * ret : 0
     */

    private int ret;
    private List<FaceBean> face;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public List<FaceBean> getFace() {
        return face;
    }

    public void setFace(List<FaceBean> face) {
        this.face = face;
    }

    public static class FaceBean {
        /**
         * position : {"top":116,"left":125,"right":377,"bottom":368}
         */

        private PositionBean position;

        public PositionBean getPosition() {
            return position;
        }

        public void setPosition(PositionBean position) {
            this.position = position;
        }

        public static class PositionBean {
            /**
             * top : 116
             * left : 125
             * right : 377
             * bottom : 368
             */

            private int top;
            private int left;
            private int right;
            private int bottom;

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getRight() {
                return right;
            }

            public void setRight(int right) {
                this.right = right;
            }

            public int getBottom() {
                return bottom;
            }

            public void setBottom(int bottom) {
                this.bottom = bottom;
            }
        }
    }
}
