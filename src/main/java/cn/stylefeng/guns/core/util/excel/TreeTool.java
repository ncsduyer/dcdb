package cn.stylefeng.guns.core.util.excel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 处理tree结构的数据 工具类
 * Created by wtj on 2018/5/20
 */
public class TreeTool {
    /**
     * 通过父id 获取树的深度
     *
     * @param list
     * @param fid
     * @param step
     * @return
     */
    public static int getTreeStep(List<Column> list, String fid, int step) {
        for (Column cc : list) {
            if (fid.equals(cc.getId())) {
                int temp = step + 1;
                return getTreeStep(list, cc.getPid(), temp);
            }
        }
        return step;
    }

    /**
     * 通过父id 获取树最大的深度
     *
     * @param list
     * @param fid
     * @return
     */
    public static int getMaxStep(List<Column> list, String fid) {
        List<Integer> nums = new ArrayList<Integer>();
        for (Column cc : list) {
            nums.add(getTreeStep(list, cc.getId(), 0));
        }
        return Collections.max(nums);
    }

    /**
     * 获取最底部子节点的个数 所有叶子节点个数
     *
     * @param list
     * @param did
     * @return
     */
    public static int getDownChilren(List<Column> list, String did) {
        int sum = 0;
        for (Column cc : list) {
            if (did.equals(cc.getPid())) {
                sum++;
                //判断该节点 是否有子节点
                if (hasChild(list, cc)) {
                    sum += getDownChilren(list, cc.getId()) - 1;
                }
            }
        }
        return sum;
    }

    /**
     * 判断是否有子节点
     *
     * @param list 遍历的数据
     * @param node 某个节点
     * @return
     */
    public static boolean hasChild(List<Column> list, Column node) {
        return getChildList(list, node).size() > 0 ? true : false;
    }

    /**
     * 得到子节点列表
     *
     * @param list 遍历的数据
     * @param node 某个节点
     * @return
     */
    public static List<Column> getChildList(List<Column> list, Column node) {
        List<Column> nodeList = new ArrayList<Column>();
        Iterator<Column> it = list.iterator();
        while (it.hasNext()) {
            Column n = (Column) it.next();
            if (n.getPid() != null && n.getPid().equals(node.getId())) {
                nodeList.add(n);
            }
        }
        return nodeList;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public static List<Column> buildByRecursive(List<Column> treeNodes) {
        List<Column> trees = new ArrayList<Column>();
        for (Column treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public static Column findChildren(Column treeNode, List<Column> treeNodes) {
        for (Column it : treeNodes) {
            if (treeNode.getId().equals(it.getPid())) {
                if (treeNode.getListTpamscolumn() == null) {
                    treeNode.setListTpamscolumn(new ArrayList<Column>());
                }
                treeNode.getListTpamscolumn().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}
