<template>
  <div class="account-list">
    <!-- SearchForm S -->
    <el-form :inline="true" :model="searchForm" class="demo-form-inline">
      <el-form-item>
        <el-input placeholder="请输入名称" clearable v-model="searchForm.name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="onSearch">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onAdd">添加</el-button>
      </el-form-item>
    </el-form>
    <!-- SearchForm E -->

    <!-- table S -->
    <el-table-page border :url="indexURL" useNewParamsBuilder ref="tablePage" :params.sync="searchForm" style="width: 100%">
      <el-table-column label="ID" width="76">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      # for(column in table.columns){ #
        # if(!column.pk) { #
        <el-table-column label="@{column.remarks}"  prop="@{utils.toLowerCaseFirst(column.columnJavaName)}" />
        # } #
      # } #
      <el-table-column label="操作" min-width="250">
        <template slot-scope="scope">
          <el-button size="small" type="primary" icon="edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="small" type="danger" icon="edit" @click="delete@{table.className}(scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table-page>
    <!-- table S -->

    <@{table.fclClassName}-dialog ref="formDialog" @update="onSearch"></@{table.fclClassName}-dialog>
  </div>
</template>

<script>
  import @{table.fclClassName}API from '@/api/@{table.fclClassName}';
  import @{table.fclClassName}Dialog from './dialog';

  export default {
    name: 'admin',
    components: { @{table.fclClassName}Dialog },
    data() {
      return {
        permissionTreeData: [],
        permissionDialogVisible: false,
        searchForm: {
          name: '',
        },
        selectedPositionIds: [],
      };
    },
    computed: {
      indexURL() {
        return @{table.fclClassName}API.indexURL;
      },
    },
    watch: {
    },
    created() {
    },
    mounted() {
      this.onSearch();
    },
    methods: {
      // 搜索
      onSearch() {
        this.$refs.tablePage.fetch();
      },
      // 添加
      onAdd() {
        this.$refs.formDialog.add({});
      },
      // 编辑
      handleEdit(idx, row) {
        Promise.all([
          @{table.fclClassName}API.show(row.id),
        ])
        .then((vals) => {
          const data = vals[0].data
          this.$refs.formDialog.update(data);
        });
      },

      delete@{table.className}(row) {
        this.$confirm(`删除后将无法恢复，确定要删除${row.name}吗？`, '删除提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning',
        }).then(() => {
          @{table.fclClassName}API.delete@{table.className}(row.id)
          .then(() => {
            this.$message({
              type: 'success',
              message: '删除成功!',
            });
            // 操作treenode完成删除新节点
            this.onSearch();
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
          });
        });
      },
    },
  };
</script>
<style lang="scss" scoped>
  .position-name{
    color:\#409EFF;
    text-decoration: underline;
    cursor: pointer;
  }
</style>
