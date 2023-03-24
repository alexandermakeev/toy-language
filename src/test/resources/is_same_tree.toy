#
# Determine if two binary trees are identical following these rules:
# - root nodes have the same value
# - left sub trees are identical
# - right sub trees are identical
#

class TreeNode [ value, left, right ]
end

fun is_same_tree [ node1, node2 ]
    if node1 == null and node2 == null
        return true
    end
    if node1 != null and node2 != null
        return node1 :: value == node2 :: value and is_same_tree [ node1 :: left, node2 :: left ] and is_same_tree [ node1 :: right, node2 :: right ]
    end
    return false
end

#                               tree-s example
#
#          tree1            tree2            tree3            tree4
#            3                3                3                3
#          /   \            /   \            /   \            /   \
#         4     5          4     5          4     5          4     5
#       /  \   /  \      /  \   /  \      /  \   / \        /  \     \
#      6    7 8    9    6    7 8    9    6    7 9   8      6    7     9

# tree1 nodes
tree1_node9 = new TreeNode [ 9, null, null ]
tree1_node8 = new TreeNode [ 8, null ]
tree1_node7 = new TreeNode [ 7 ]
tree1_node6 = new TreeNode [ 6 ]
tree1_node5 = new TreeNode [ 5, tree1_node8, tree1_node9 ]
tree1_node4 = new TreeNode [ 4, tree1_node6, tree1_node7 ]
tree1 = new TreeNode [ 3, tree1_node4, tree1_node5 ]

tree2 = new TreeNode [ 3, new TreeNode [ 4, new TreeNode [ 6 ], new TreeNode [ 7 ] ], new TreeNode [ 5, new TreeNode [ 8 ], new TreeNode [ 9 ] ] ]
tree3 = new TreeNode [ 3, new TreeNode [ 4, new TreeNode [ 6 ], new TreeNode [ 7 ] ], new TreeNode [ 5, new TreeNode [ 9 ], new TreeNode [ 8 ] ] ]
tree4 = new TreeNode [ 3, new TreeNode [ 4, new TreeNode [ 6 ], new TreeNode [ 7 ] ], new TreeNode [ 5, null, new TreeNode [ 9 ] ] ]

assert is_same_tree [ tree1, tree2 ]
assert !is_same_tree [ tree1, tree3 ]
assert !is_same_tree [ tree2, tree3 ]
assert !is_same_tree [ tree1, tree4 ]
assert !is_same_tree [ tree2, tree4 ]
assert !is_same_tree [ tree3, tree4 ]