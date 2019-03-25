package t04;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 同步容器
 * @author: hz16092620
 * @create: 2019-03-25 16:49
 */
public class PracticeConcurrentContainer {


}

class ConcurrentContainer {

    int maxSize = 10;

    List<String> list = new ArrayList<String>();

    public void addE(String str) throws Exception {
        synchronized (this) {
            if (this.size() < 10) {
                this.list.add(str);
            } else {
                throw new Exception("超出最大值 " + maxSize);
            }
        }
    }

    public int size() {
        return list.size();
    }

    //public String get()

    public ConcurrentContainer () {
    }
    public ConcurrentContainer (int maxSize) {
        this.maxSize = maxSize;
    }
}


