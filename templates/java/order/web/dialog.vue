<template>
  <div class="@{table.fclClassName}-dialog">
    <el-dialog :title="title" :visible.sync="dialogVisible" custom-class="main-account-dialog" :before-close="handleClose">
      <el-form :model="@{table.fclClassName}Form" status-icon label-position="left" :rules="rules" ref="@{table.fclClassName}Form" label-width="80px" class="demo-ruleForm">

        # for(column in table.columns){ #
          # if(!column.pk && column.columnName != 'create_time' && column.columnName != 'update_time' ){ #
          <el-row>
            <el-col :span="15">
              <el-form-item label="@{column.remarks}" prop="name">
                <el-input v-model="@{table.fclClassName}Form.@{utils.toLowerCaseFirst(column.columnJavaName)}" placeholder="请填写@{column.remarks}"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          # } #
        # } #

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import @{table.fclClassName}API from '@/api/@{table.fclClassName}';

  export default {
    data() {
      return {
        props:{ value:'id',multiple:true ,expandTrigger:'hover' },
        dialogVisible: false,
        updateType: 'create',
        @{table.fclClassName}Form: {
          # for(column in table.columns){ #
            # if(!column.pk && column.columnName != 'create_time' && column.columnName != 'update_time' ){ #
          @{utils.toLowerCaseFirst(column.columnJavaName)}: '',
            # } #
          # } #
        },
        rules: {
        },
        title: '',
      };
    },
    watch: {
    },
    created() {
    },
    methods: {
      emitUpdate() {
        this.$emit('update');
      },
      update(data) {
        this.title = '编辑@{table.remarks}';
        this.updateType = 'update';
        this.@{table.fclClassName}Form = data;
        this.openDialog();
      },
      add(data) {
        this.title = '添加@{table.remarks}';
        this.updateType = 'create';
        this.openDialog();
      },

      handleClose(done) {
        this.resetForm();
        this.dialogVisible = false;
      },
      openDialog() {
        this.dialogVisible = true;
      },
      closeDialog() {
        this.resetForm();
        this.dialogVisible = false;
      },
      resetForm(){
        # for(column in table.columns){ #
          # if(!column.pk && column.columnName != 'create_time' && column.columnName != 'update_time' ){ #
        this.@{table.fclClassName}Form.@{utils.toLowerCaseFirst(column.columnJavaName)} = ''
          # } #
        # } #
      },
      submitForm() {
        this.$refs.@{table.fclClassName}Form.validate((valid) => {
          if (valid) {
            if (this.updateType === 'create') {
              @{table.fclClassName}API.create(this.@{table.fclClassName}Form)
              .then((res) => {
                if (res.code === 200) {
                  this.$message({
                    type: 'success',
                    message: '创建成功',
                  });
                  this.closeDialog();
                  this.emitUpdate();
                } else {
                  this.$message({
                    type: 'error',
                    message: res.msg,
                  });
                }
              });
            } else if (this.updateType === 'update') {
              @{table.fclClassName}API.update(this.@{table.fclClassName}Form)
              .then((res) => {
                if (res.code === 200) {
                  this.$message({
                    type: 'success',
                    message: '更新成功',
                  });
                  this.closeDialog();
                  this.systems = '';
                  this.emitUpdate();
                } else {
                  this.$message({
                    type: 'error',
                    message: res.msg,
                  });
                }
              });
            }
          } else {
            console.error('error validate!!');
          }
        });
      },
    },
  };
</script>
<style lang="scss">
  .@{table.fclClassName}-dialog .main-account-dialog{
    min-width: 660px;
  }
</style>
