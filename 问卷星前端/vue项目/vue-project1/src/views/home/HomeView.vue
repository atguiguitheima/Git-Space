
  <template>
  <div>
    <!-- 先引入container布局组件 -->
    <el-container style="height: 900px; border: 1px solid #eee">
      <!--再设置标题的内容和css样式 -->
      <el-header height="90px" style="font-size: 40px; background-color: rgb(238, 241, 246)"
        >问卷星          
        </el-header
      >
      <el-container>
        <el-aside width="200px" style="width: 210px; border: 1px solid #eee">
          <el-row>
            <br />
            <router-link to="/create">
              <el-button style="margin-left: 10px">创建问卷</el-button>
            </router-link>
          </el-row>
          <el-row>
            <br />
              <el-button  style="margin-left: 10px"
              @click="updatePasswordVisible = true"
                    >修改密码</el-button>
                   
              

<el-dialog
  title="修改用户密码"
  :visible.sync="updatePasswordVisible"
  width="30%"
  :before-close="handleClose">
  <span>{{'新的密码:&nbsp;&nbsp;&nbsp;'}} <el-input v-model="newPassword" placeholder="请输入密码"></el-input>
  </span>
  <span slot="footer" class="dialog-footer">
    <el-button @click="updatePasswordVisible = false">取 消</el-button>
    <el-button type="primary" @click="updatePassword">确认修改</el-button>
  </span>
</el-dialog>


          </el-row><el-row>
            <br />
            
              <el-button style="margin-left: 10px"
                  @click="logout" >注销账号</el-button>

          </el-row>
        </el-aside>
        <el-main>
          <!-- 表单 -->
          <div class="demo-form-inline" :inline="true"><h1>问卷列表</h1></div>
          <div>
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item label="查询问卷">
                <el-input v-model="qnTitle"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmit">查询</el-button>
              </el-form-item>
            </el-form>
          </div>

          <!-- 表格 -->
          <el-table :data="questionnaireTitle" style="width: 100%">
            <el-table-column width="0">
              <template slot-scope="scope">
                <div href="">{{ scope.row.qnTitle }}</div>
              </template>
            </el-table-column>

            <el-table-column label="">
              <template slot-scope="scope">
                <el-button
                  type="text"
                  @click="buQn(scope.row.qnId, scope.row.qnTitle)"
                  size="small"
                  >修改</el-button
                >
                <el-dialog
                  :title="scope.row.qnTitle"
                  :visible.sync="updateVisible"
                  width="100%"
                >
                  <div v-if="qn != {}">
                    <Update :qn1="qn"></Update>
                  </div>

                  <div slot="footer" class="dialog-footer">
                    <el-button @click="updateVisible = false">取 消</el-button>
                    <el-button type="primary" @click="updateQn(scope.row.qnId)"
                      >完成</el-button
                    >
                  </div>
                </el-dialog>

                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>

                <el-button
                  type="text"
                  @click="dialogVisible = true"
                  size="small"
                  >删除</el-button
                >

                <el-dialog
                  title="提示"
                  :visible.sync="dialogVisible"
                  width="30%"
                  :before-close="handleClose"
                >
                  <span>确认是否删除问卷!</span>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="dlQn(scope.row.qnId)"
                      >确 定</el-button
                    >
                  </span>
                </el-dialog>

                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>

                <el-button
                  type="text"
                  size="small"
                  @click="showQn(scope.row.qnId)"
                  >查看问卷</el-button
                >

                <el-dialog
                  :title="scope.row.qnTitle"
                  :visible.sync="dialogFormVisible"
                >
                  <el-form ref="form" :model="qn">
                    <div v-for="(item, index) in qn.qtList" :key="item.qnQtId">
                      <div v-if="item.qnQtType == 1 ? true : false">
                        {{ index + 1 + "、" + item.qnQtTitle + "(单选题)" }}

                        <div v-for="item1 in qn.otList" :key="item1.otId">
                          <span v-if="item.qnQtId == item1.qtId">
                            <input type="radio" :name="item.qnQtId" />{{
                              item1.otTitle
                            }}
                            <br />
                          </span>
                        </div>
                      </div>

                      <div v-if="item.qnQtType == 2 ? true : false">
                        {{ index + 1 + "、" + item.qnQtTitle + "(多选题)" }}
                        <div></div>
                        <div v-for="item1 in qn.otList" :key="item1.otId">
                          <span v-if="item.qnQtId == item1.qtId">
                            <input type="checkbox" :name="item.qnQtId" />{{
                              item1.otTitle
                            }}
                            <br />
                          </span>
                        </div>
                      </div>

                      <div v-if="item.qnQtType == 3 ? true : false">
                        {{ index + 1 + "、" + item.qnQtTitle + "(填空题)" }}
                        <span><input placeholder="请输入内容" /></span>
                        <div
                          v-for="item1 in qn.otList"
                          :key="item1.otIld"
                        ></div>
                      </div>
                      <br />
                    </div>
                  </el-form>
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false"
                      >取 消</el-button
                    >
                    <el-button type="primary" @click="dialogFormVisible = false"
                      >确 定</el-button
                    >
                  </div>
                </el-dialog>

                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>

                <el-button
                  type="text"
                  size="small"
                  @click="generateAnswer(scope.row.userId, scope.row.qnId)"
                  >导出问卷</el-button
                >

                <el-dialog
                  title="问卷填写地址"
                  :visible.sync="urlVisible"
                  width="30%"
                >
                  <span>{{ url }}</span>
                  <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="urlVisible = false"
                      >确 定</el-button
                    >
                  </span>
                </el-dialog>

                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <!-- <el-button
                  type="text"
                  size="small"
                  @click="answerDeadline = true"
                  >设置问卷填写的截止时间</el-button
                >{{ scope.row.qnId }} -->

                <el-popover
                  placement="left"
                  title="问卷截止时间"
                  width="350"
                  trigger="click"
                  content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。"
                >
                  <el-date-picker
                    v-model="deadline"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-ddTHH:mm:ss"
                  >
                  </el-date-picker>
                  <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                  <el-button
                    type="primary"
                    @click="setAnswerDeadline(scope.row.qnId)"
                    >确 定</el-button
                  >
                  <el-button type="text" size="small" slot="reference"
                    >设置问卷填写的截止时间</el-button
                  >
                </el-popover>

                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                  <el-popover
                  placement="left"
                  width="200"
                  trigger="click"
                  ><div v-bind="deadline1">{{ '该问卷截止时间为: '+deadline1 }}</div>
                  <el-button slot="reference" type="text" size="small" @click="showDeadline(scope.row.userId,scope.row.qnId)">查看问卷截止时间</el-button>
                </el-popover>
                <!-- <br>
                  <el-date-picker
                    v-model="deadline"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-ddThh:mm:ss"
                  >
                  </el-date-picker>
                 
                  <el-button type="text" size="small" slot="reference" @click="setAnswerDeadline(scope.row.qnId)"
                    >设置问卷填写的截止时间</el-button>
          -->
                <!-- 
                <div class="block">
                  <span class="demonstration"></span>
                  <el-date-picker
                    v-model="deadline"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-ddThh:mm:ss"
                  >
                  </el-date-picker>
                </div>
                <span slot="footer" class="dialog-footer">
                  <el-button
                    type="primary"
                    @click="setAnswerDeadline(scope.row.qnId)"
                    >确 定</el-button
                  >{{ scope.row.qnId }}
                  <el-button @click="answerDeadline = false">取 消</el-button>
                </span> -->
              </template>
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
  
  <script>
import Update from "../function/UpdateView.vue";
export default {
  components: {
    Update,
  },
  data() {
    return {
      newPassword:'',
      updatePasswordVisible:false,
      deadline1:'',
      visible: false,
      answerDeadline: false,
      deadline: "",
      url: "",
      urlVisible: false,
      qnTitle: "",
      updateVisible: false,
      dialogFormVisible: false,
      dialogVisible: false,
      currentPage1: 1,
      qn: {},
      questionnaireTitle: [],
      empData: [],
      submitInformation: {
        name: "",
        gender: "",
        entrydate: "",
      },
      user: {
        uname: "",
        uid: null,
      },
    };
  },
  methods: {
    updatePassword(){
      if(this.newPassword==''||this.newPassword==null){
        this.$message({
            message: "密码不能为空",
            type: "warning",
          });
      }else{
        this.updatePasswordVisible=false;
        this.axios({
            url: "/api/users/modify",               // 请求地址
            method: "put",                       // 请求方法
            headers: {                            // 请求头
              "Content-Type": "application/json",
              token: localStorage.getItem("userjwt"),
            },
            data: JSON.stringify(this.newPassword),
          }).then((res) => {
            if(res.data.code==1){
              this.$message({
            message: "密码修改成功,请重新登录",
            type: "success",
          });
          this.logout();
            }
          })

      }
    },
    showDeadline(userId,qnId){
      this.axios({
        url: "/api/users/deadline", // 请求地址
        headers: {
          token: localStorage.getItem("userjwt"),
        },
        params: {
          qnId: qnId,
          userId:userId
        },
        method: "get", // 请求方法
      }).then((res) => {
        // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
        console.log(res.data.data);
       this.deadline1=res.data.data;
      });
    },
    setAnswerDeadline(qnId) {
      console.log(qnId);
      this.answerDeadline = false;
      console.log(this.deadline);
      if (this.deadline != "" && this.deadline != null) {
        this.axios({
          url: "/api/users/deadline", // 请求地址
          headers: {
            token: localStorage.getItem("userjwt"),
            "Content-Type": "application/json",
          },
          data: JSON.stringify({
            qnId: qnId,
            deadline: this.deadline,
          }),
          method: "post", // 请求方法
        }).then((res) => {
          // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
          console.log(res.data.data);
          this.$message({
            message: "设置成功",
            type: "success",
          });
        });
      } else {
        this.answerDeadline = true;
        this.$message({
          message: "设置失败,未选择时间",
          type: "warning",
        });
      }
    },
    generateAnswer(userId, qnId) {
      (this.urlVisible = true),
        (this.url =
          "http://localhost:8081/answer?" +
          "userId=" +
          userId +
          "&qnId=" +
          qnId);
    },
    buQn(qnId, qnTitle) {
      this.axios({
        url: "/api/users/qn", // 请求地址
        headers: {
          token: localStorage.getItem("userjwt"),
        },
        params: {
          qnId: qnId,
        },
        method: "get", // 请求方法
      }).then((res) => {
        // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
        console.log(res.data.data);
        this.qn = res.data.data;
        this.qn.qn.qnTitle = qnTitle;
        console.log(this.qn);
        this.updateVisible = true;
      });
    },
    updateQn(qnId) {
      this.updateVisible = false;
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    dlQn(qnId) {
      this.dialogVisible = false;
      this.axios({
        url: "/api/users", // 请求地址
        headers: {
          token: localStorage.getItem("userjwt"),
        },
        params: {
          qnId: qnId,
        },
        method: "delete", // 请求方法
      }).then((res) => {
        // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
        console.log(res.data.data);
        this.qn = res.data.data;
      });
      location.reload();
    },
    showQn(qnId) {
      this.dialogFormVisible = true;
      this.axios({
        url: "/api/users/qn", // 请求地址
        headers: {
          token: localStorage.getItem("userjwt"),
        },
        params: {
          qnId: qnId,
        },
        method: "get", // 请求方法
      }).then((res) => {
        // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
        console.log(res.data.data);
        this.qn = res.data.data;
      });
    },
    logout() {
      this.axios({
        url: "/api/users/logout", // 请求地址
        headers: {
          token: localStorage.getItem("userjwt"),
        },
        params: {
          autoLogin: false,
        },
        method: "get", // 请求方法
      }).then((res) => {
        // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
        localStorage.setItem("userjwt",res.data.data)
         // 移除本地用户登录信息
      localStorage.removeItem("userInfo");
      // 跳转页面到登录页
      this.$router.push("/login");
      });
    },
    onSubmit() {
      if (this.qnTitle != "") {
        this.axios({
          url: "/api/users/conditionQns", // 请求地址
          headers: {
            token: localStorage.getItem("userjwt"),
          },
          params: {
            qnTitle: this.qnTitle,
          },
          method: "get", // 请求方法
        }).then((res) => {
          // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
          console.log(res.data.data);
          this.questionnaireTitle = res.data.data;
        });
      } else {
        this.axios({
          url: "/api/users", // 请求地址
          headers: {
            token: localStorage.getItem("userjwt"),
          },
          method: "get", // 请求方法
        }).then((res) => {
          // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
          console.log(res.data.data);
          this.questionnaireTitle = res.data.data;
        });
      }
    },
    handleSizeChange: function (val) {
      alert("当前每页展示变为" + val);
    },
    handleCurrentChange(val) {
      alert("当前页数变为" + val);
    },
  },
  mounted() {
    /* 
      if (sessionStorage.getItem('userInfo')) {
        // 将用户信息存储到sessionStorage中
        this.user = JSON.parse(sessionStorage.getItem('userInfo'));
      }
      */
    this.axios({
      url: "/api/users", // 请求地址
      headers: {
        token: localStorage.getItem("userjwt"),
      },
      method: "get", // 请求方法
    }).then((res) => {
      // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
      console.log(res.data.data);
      this.questionnaireTitle = res.data.data;
    });
  },
};
</script>
  
  <style scoped>
    .boxx {
    width:2500px ;
    height: 0px;
} 
 .boxy {
    width:2700px ;
    height: 0px;
}  
/* .box {
  width: 300px;
  height: 300px;
  border: 0px solid skyblue;
} */
/* .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  } */

.demo-form-inline {
  display: inline-block;
}
</style>