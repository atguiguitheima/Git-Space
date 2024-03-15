<template>
  
    <div>
  
        <el-form ref="form" :model="qn"  label-position="left"
        label-width="70px"
        class="login-from">
                    <div >{{ qn.qn.qnTitle }}</div>
                    <br>
                    <div v-for="(item, index) in qn.qtList" :key="item.qnQtId">
                      <div v-if="item.qnQtType == 1 ? true : false">
                        {{ (index + 1) + '、' + item.qnQtTitle + '(单选题)' }}

                        <div v-for="item1 in qn.otList" :key="item1.otId">
                          <span v-if="item.qnQtId == item1.qtId">
                            <input type="radio" :name="item.qnQtId" :value="item1.otId"  @input="selectRadio(item1.qtId,item1.otId)"/>{{
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
                            <input type="checkbox" :name="item.qnQtId" :value="item1.otId" @input="selectCheckBox(item1.qtId,item1.otId)" />{{
                              item1.otTitle
                            }}
                            <br />
                          </span>
                        </div>
                      </div>

                      <div v-if="item.qnQtType == 3 ? true : false">
                        {{ index + 1 + "、" + item.qnQtTitle + "(填空题)" }}
                        <br/>
                        <span><input type="text"  @input="selectText(item.qnQtId,$event)"/></span>
                        <div
                          v-for="item1 in qn.otList"
                          :key="item1.otIld"
                        ></div>
                      </div>
                      <br />
                    </div>
                    <br/>
                    <el-button type="primary" @click="submitAnswer">提交问卷</el-button>
                  </el-form>
  
  </div>

 
</template>

<script>
export default {
    
    data () {
        return {
            deadline:'',
            answers:[],
            qn: {},
        }
    },
    methods:{
        submitAnswer(){
         
      
            this.axios({
            url: "/api/users/answer",               // 请求地址
            method: "post",
            headers: { 
              "token":localStorage.getItem("userjwt"),                           // 请求头
              "Content-Type": "application/json",
            },
            data:JSON.stringify(this.answers),                       // 请求方法
          }).then((res) => { // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
            console.log(res.data.data);
            this.$message({
                message: '成功提交',
                type: "success",
              });
          });
        }
      ,
        selectRadio(qtId,otId){
            console.log('触发了')
            for(let i=0;i<this.answers.length;i++){
                if(this.answers[i].qnQtId==qtId){
                    if(this.answers[i].answer==otId){
                    this.answers[i].sign='是';}
                    else{
                        this.answers[i].sign='否'
                    }
                }
            }
           
        }, selectCheckBox(qtId,otId){
            console.log('触发了')
            for(let i=0;i<this.answers.length;i++){
                if(this.answers[i].qnQtId==qtId&&this.answers[i].answer==otId){
                    if(this.answers[i].sign=='否'){
                        console.log('没被选')
                        this.answers[i].sign='是';
                    }else{
                        console.log('被选了')
                        this.answers[i].sign='否';
                    }
                    console.log(this.answers[i].sign)
                }
            }
           
        }, selectText(qnQtId,event){
            for(let i=0;i<this.answers.length;i++){
                if(this.answers[i].qnQtId==qnQtId){
                    this.answers[i].answer=event.currentTarget.value;
                    this.answers[i].sign='是';
                }
            }
        }
    },
    mounted () {
        let url=window.location.search;
        let userId=url.substring(8,10);
        let qnId=url.substring(16);

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
        if(res.data.code==1){
          this.deadline = res.data.data;
          console.log(res.data.data)


          var date = new Date();
var seperator1 = "-";
var seperator2 = ":";
var month = date.getMonth() + 1<10? "0"+(date.getMonth() + 1):date.getMonth() + 1;
var strDate = date.getDate()<10? "0" + date.getDate():date.getDate();
var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
+ "T" + date.getHours() + seperator2 + date.getMinutes() + seperator2 + date.getSeconds();
/* console.log(currentdate) ; 
                  console.log(this.deadline)    
                  console.log(currentdate<JSON.stringify(this.deadline));
                  console.log("2024-03-14T23:20:36"<"2024-03-15T23:51:27");
                  console.log("2024-03-15T23:51:27")
                  console.log(JSON.stringify(currentdate))
                  console.log(JSON.stringify(this.deadline).substring(0,1)) */
          if(JSON.stringify(currentdate)<JSON.stringify(this.deadline)){
        this.axios({
        url: "/api/users/answer", // 请求地址
        headers: {
          token: localStorage.getItem("userjwt"),
        },
        params: {
            userId:userId,
            qnId: qnId,
        },
        method: "get", // 请求方法
      }).then((res) => {
        // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
        console.log(res.data.data);
        this.qn = res.data.data;
        for(let i=0;i<this.qn.otList.length;i++){
            this.answers.push({userId:userId,qnId:qnId,qnQtId:this.qn.otList[i].qtId,answer:this.qn.otList[i].otId,sign:'否'});
        }
        console.log(this.answer)
      });
    }else{
          this.$message({
                message: '该问卷已过期,请联系发布人!',
                type: "warning",
              });
        }
        }
      });
      
    
                /*   console.log(currentdate) ; 
                  console.log(this.deadline)    
                  console.log(currentdate<JSON.stringify(this.deadline)); */
/* var deadlinedatetime=(this.deadline).toString 
var deadlineyear=deadlinedatetime.substring(0,4);
var deadlinemonthandday=deadlinedatetime.substring(5,10);
var deadlinetimeh=deadlinedatetime.substring(11,13);              
var deadlinetimem=deadlinedatetime.substring(14,16);              
var deadlinetimes=deadlinedatetime.substring(17);

var result=false;
console.log(date.getFullYear<deadlineyear)
if(date.getFullYear<deadlineyear){
  
} */
/* console.log(currentdate)
console.log(JSON.stringify(this.deadline))
console.log("2023-09-23T20:82:00">"2023-09-23T20:81:00") */
      
}
}
</script>

<style scoped>
.box-card {
  margin: 150px 500px;
  width: 400px;
}
/* 设置登录面板中的表单居中 */
.login-from {
  margin: auto auto;
}

#building{
  background:url("D:\问卷星前端\vue项目\vue-project1\src\assets\学生管理系统背景.png");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
}
</style>