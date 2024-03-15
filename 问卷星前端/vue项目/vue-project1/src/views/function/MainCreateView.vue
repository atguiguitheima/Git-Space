<template>
    <div>
        
    </div>
</template>

<script>
import Bus from '../../utils/EventBus.js'
export default {
    data () {
        return {
            qnTitle:'',
        }
    },
    methods: {
        confirm(){
            if(this.qnTitle==''){
                alert("问卷名称不能为空")
            }else{
                console.log(this.qnTitle)
                Bus.$emit('qnTitle',this.qnTitle)
                this.$router.push('/create');
            }
        }
    },
}
</script>

<style scoped>

</style>