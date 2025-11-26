//frontend/src/views/Join.vue
//회원 정보 컴포넌트

<script setup>
import {reactive} from "vue";
import {withdraw, getInfo} from "@/services/accountService";
import {useRouter} from "vue-router";
import {useAccount} from "@/stores/account";

// 계정 스토어
const accountStore = useAccount();

// 반응형 상태
const state = reactive({
  form: {
    name: "",
    loginId: "",
    loginPw: "",
    address: "",
  }
});

// 라우터 객체
const router = useRouter();

// 홈으로 이동
const cancel = async () => {
    await router.push("/");
};

// 회원 탈퇴
const deleteInfo = async () => {
  window.confirm("정말로 탈퇴하시겠습니까?");
  const res = await withdraw(accountStore.id);
  if(res.status === 200){
    window.alert("탈퇴되었습니다.");
  } else{
    window.alert("오류 발생. 탈퇴 실패");
  }
};

(async function onCreated() { // ④
  const info = await getInfo();
  state.form.name = info.data.name;
  state.form.loginId = info.data.loginId;
  state.form.loginPw = info.data.loginPw;
  state.form.address = info.data.address;
})();
</script>

<template>
  <div class="join">
    <div class="container">
      <form class="py-5 d-flex flex-column gap-3" @submit.prevent="deleteInfo">
        <h1 class="h5 mb-3">회원정보</h1>
        <div class="form-floating">
          <input type="text" class="form-control" id="name" placeholder="이름" v-model="state.form.name" readonly>  <!-- ⑥ -->
          <label for="name">이름</label>
        </div>
        <div class="form-floating" v-if="false">
          <input type="email" class="form-control" id="loginId" placeholder="이메일" v-model="state.form.loginId"> <!-- ⑥ -->
          <label for="loginId">아이디</label>
        </div>
        <div class="form-floating" v-if="false">
          <input type="password" class="form-control" id="loginPw" placeholder="패스워드" v-model="state.form.loginPw"> <!-- ⑥ -->
          <label for="loginPw">패스워드</label>
        </div>
        <div class="form-floating">
          <input type="text" class="form-control" id="address" placeholder="주소" v-model="state.form.address" readonly> <!-- ⑥ -->
          <label for="address">주소</label>
        </div>
        <button type="button" class="w-100 h6 btn py-3 btn-primary" @click="cancel">Home</button>&nbsp
        <button type="button" class="w-100 h6 btn py-3 btn-primary" @click="deleteInfo">회원탈퇴</button>
      </form>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.join > .container { // ⑧
  max-width: 576px;
}
</style>