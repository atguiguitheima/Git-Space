<template>
    <div class="big_box">
      <el-container>
        <el-aside width="700px">
          <div class="box">
         
            <br/>
            问卷名称:<input type="text" v-model="qn.qn.qnTitle" >
            <br/>
            <br/>
            <el-button style="margin-left: 20px" @click="createRadio"
              >创建单选题</el-button
            >
            <el-button style="margin-left: 20px" @click="createCheckbox">创建多选题</el-button>
            <el-button style="margin-left: 20px" @click="createText">创建填选题</el-button>
            <br />
            <form>
              <div v-for="(item, index) in qn.qtList" :key="item.qnQtId">
                <div v-if="item.qnQtType == 1 ? true : false">
                  {{ "第" + (index + 1) + "个问题名称(单选题)"
                  }}<input type="text" v-model="item.qnQtTitle" /> <el-button style="margin-left: 20px"  @click="dl(index,item.qnQtId)">删除</el-button>
                  <div></div>
                  <div v-for="(item1,index1) in qn.otList" :key="item1.otId" >
                <span v-if="item.qnQtId==item1.qtId">
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    {{"选项问题(单选题)"}}<input type="text" v-model="item1.otTitle"  ><el-button style="margin-left: 20px"  @click="dlOp(index1)">删除选项</el-button>
                  <br/>
                </span>
                  </div>
                  <el-button style="margin-left: 20px"  @click="createRadioOp(item.qnQtId)">添加单选选项</el-button>
                  
                </div>
  
                <div v-if="item.qnQtType == 2 ? true : false">
                  {{ "第" + (index + 1) + "个问题名称(多选题)"
                  }}<input type="text" v-model="item.qnQtTitle" /><el-button style="margin-left: 20px"  @click="dl(index,item.qnQtId)">删除</el-button>
                  <div></div>
                  <div v-for="(item1,index1) in qn.otList" :key="item1.otId" >
                <span v-if="item.qnQtId==item1.qtId">
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    {{"选项问题(多选题)"}}<input type="text" v-model="item1.otTitle"  ><el-button style="margin-left: 20px"  @click="dlOp(index1)">删除选项</el-button>
                  <br/>
                </span>
                  </div>
                  <el-button style="margin-left: 20px"  @click="createCheckboxOp(item.qnQtId)">添加多选选项</el-button>
                </div>
  
                <div v-if="item.qnQtType == 3 ? true : false">
                  {{ "第" + (index + 1) + "个问题名称(填空题)"
                  }}<input type="text" v-model="item.qnQtTitle" /><el-button style="margin-left: 20px"  @click="dl(index,item.qnQtId)">删除</el-button>
                  <div></div>
                  <div v-for="(item1) in qn.otList" :key="item1.otId" >
                <span v-if="item.qnQtId==item1.qtId">
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                   
                  <br/>
                </span>
                  </div>
                </div>
                <br />
                
              </div>
            </form>
            <br/>
            <el-button style="margin-left: 20px" @click="submitupdate" >确认修改问卷</el-button>
          </div>
        </el-aside>
        <el-main>
          <div class="box">
            <h5>预览</h5>
            <form>
              <div v-for="(item, index) in qn.qtList" :key="item.qnQtId">
                <div v-if="item.qnQtType == 1 ? true : false">
                  <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                  {{ (index + 1) + "、"+item.qnQtTitle+"(单选题)"}}
                  <div></div>
                  <div v-for="(item1) in qn.otList" :key="item1.otId" >
                <span v-if="item.qnQtId==item1.qtId">
                    <input type="radio" :name="item.qnQtId" >{{item1.otTitle}}
                  <br/>
                </span>
                  </div>
              
                </div>
  
                <div v-if="item.qnQtType == 2 ? true : false">
                  <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                  {{  (index + 1) + "、"+item.qnQtTitle+ "(多选题)"}}
                  <div></div>
                  <div v-for="(item1) in qn.otList" :key="item1.otId" >
                <span v-if="item.qnQtId==item1.qtId">
                    <input type="checkbox" :name="item.qnQtId"  >{{item1.otTitle}}
                  <br/>
                </span>
                  </div>
                  
                </div>
  
                <div v-if="item.qnQtType == 3 ? true : false">
                  <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                  {{ (index + 1) + "、"+item.qnQtTitle+ "(填空题)"
                  }}
                  <div></div>
                  <div v-for="(item1) in qn.otList" :key="item1.otId" >
                <span v-if="item.qnQtId==item1.qtId">
                  <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><input type="text" >
                  <br/>
                </span>
                  </div>
                </div>
                <br />
                
              </div>
            </form>
           
          </div>
        </el-main>
      </el-container>
    </div>
  </template>
  
  <script>
  
  export default {
    props:['qn1'],
    data() {
      return {
        qnQtId:1,
        qtId:1,
        otId:1,
        qn:this.qn1
      };
    },
    methods: {
      createRadio() {
        this.qn.qtList.push({
          id:1,
          qnQtType: 1,
          qnQtTitle: "",
          qnQtId: this.qnQtId,
          qnId:1,
          userId:1,
        });
        this.qn.otList.push({ id:null,otTitle:"", qtId:this.qnQtId, qnId:null, userId:null, otId :this.otId});
        this.otId=this.otId+1;
        this.qn.otList.push({ id:null,otTitle:"", qtId:this.qnQtId, qnId:null, userId:null, otId :this.otId});
        this.otId=this.otId+1;
        this.qnQtId=this.qnQtId+1
      },
      createCheckbox(){
        this.qn.qtList.push({
          id:1,
          qnQtType: 2,
          qnQtTitle:"",
          qnQtId: this.qnQtId,
          qnId:1,
          userId:1,
        });
        this.qn.otList.push({ id:null,otTitle:"", qtId:this.qnQtId, qnId:null, userId:null, otId :this.otId});
        this.otId=this.otId+1;
        this.qn.otList.push({ id:null,otTitle:"", qtId:this.qnQtId, qnId:null, userId:null, otId :this.otId});
        this.otId=this.otId+1; 
        this.qn.otList.push({ id:null,otTitle:"", qtId:this.qnQtId, qnId:null, userId:null, otId :this.otId});
        this.otId=this.otId+1;
         this.qn.otList.push({ id:null,otTitle:"", qtId:this.qnQtId, qnId:null, userId:null, otId :this.otId});
        this.otId=this.otId+1;
        this.qnQtId = this.qnQtId+1;
      },
      createText(){
        this.qn.qtList.push({
          id:1,
          qnQtType: 3,
          qnQtTitle: "",
          qnQtId: this.qnQtId,
          qnId:1,
          userId:1,
        });
        this.qn.otList.push({ id:null,otTitle:null, qtId:this.qnQtId, qnId:null, userId:null, otId :this.otId});
        this.otId=this.otId+1;
        this.qnQtId = this.qnQtId+1;
      },
      dl(index,qnQtId){
        console.log(index)
        console.log(qnQtId)
        this.qn.qtList.splice(index,1);
        for(let i=this.qn.otList.length;i>0;i--){
          console.log(this.qn.otList[i-1])
          if(this.qn.otList[i-1].qtId==qnQtId){
            this.qn.otList.splice(i-1,1);
          }
        }
      console.log(this.qn.opList)
      },
      createRadioOp(value){
        this.qn.otList.push({ id:null,otTitle:"", qtId:value, qnId:null, userId:null, otId :this.otId});
        this.otId=this.otId+1;
      },
      createCheckboxOp(value){
        this.qn.otList.push({ id:null,otTitle:"", qtId:value, qnId:null, userId:null, otId :this.otId});
        this.otId=this.otId+1;
      },
      dlOp(value){
        this.qn.otList.splice(value,1);
      },
    
      submitupdate(){
        
        
       
        if(this.qn.qn.qnTitle==''){
                  alert("问卷名称不能为空")
              }else{
                this.axios({
              url: "/api/users",               // 请求地址
              method: "put",
              headers: { 
                "token":localStorage.getItem("userjwt"),                           // 请求头
                "Content-Type": "application/json",
              },
              data:JSON.stringify(this.qn),                       // 请求方法
            }).then((res) => { // 当收到后端的响应时执行该括号内的代码，res 为响应信息，也就是后端返回的信息
              this.$router.replace('/')
            });
            this.$message({
                  message: '成功修改',
                  type: "success",
                });
              }
              
      }
     
    },
    mounted(){
        
        var qn=this.qn1
        var qnQtId=0;
        var otId=0;
        console.log(qn)
        for(let i=0;i<qn.qtList.length;i++){
            if(qn.qtList[i].qnQtId>qnQtId){
                qnQtId=qn.qtList[i].qnQtId;
            }
        }  for(let i=0;i<qn.otList.length;i++){
            if(qn.otList[i].otId>otId){
                otId=qn.otList[i].otId;
            }
        }
        this.qnQtId=qnQtId+1;
        this.otId=otId+1;
        console.log(this.qnQtId);
        console.log(this.otId);
    }
  };
  
 
  </script>
  
  <style scoped>
  * {
    margin: 0 auto;
    padding: 0;
  }
  .box {
    width: 600px;
    height: 800px;
    border: 0px solid skyblue;
  }
  .item {
    width: 100px;
    height: 100px;
    margin: 50px;
    padding: 10px;
    background: orange;
    border: 5px solid red;
  }
  </style>