<script setup>
import {reactive, computed} from "vue";
import {withdraw, getInfo, updateInfo} from "@/services/accountService";
import {useRouter} from "vue-router";
import {useAccount} from "@/stores/account";

const accountStore = useAccount();
const router = useRouter();

const state = reactive({
  isEditMode: false, // 수정 모드 여부 (false: 조회, true: 수정)
  form: {
    name: "",
    loginId: "",
    loginPw: "",
    address: "",
  },
  original: {} // 수정 취소 시 복구할 원본 데이터
});

// 아이디/비밀번호 마스킹 처리 (조회 모드용)
const maskedLoginId = computed(() => {
  return state.isEditMode ? state.form.loginId : "*".repeat(state.form.loginId.length || 8);
});

const maskedLoginPw = computed(() => {
  const pw = state.form.loginPw || ""; // undefined이면 ""로 대체
  return state.isEditMode ? pw : "*".repeat(pw.length || 8);
});

// 수정 모드 진입
const startEdit = () => {
  // 현재 데이터를 원본에 백업
  state.original = { ...state.form };
  state.isEditMode = true;
};

// 수정 취소
const cancelEdit = () => {
  // 원본 데이터로 복구
  state.form = { ...state.original };
  state.isEditMode = false;
};

// 정보 수정 저장
const submitUpdate = async () => {
  if (!state.form.loginId || !state.form.loginPw || !state.form.name) {
    alert("필수 정보를 모두 입력해주세요.");
    return;
  }

  // 아이디 변경 확인
  if (state.form.loginId !== state.original.loginId) {
    if (!confirm("아이디를 변경하시겠습니까? 저장 후 변경된 아이디로 로그인해야 합니다.")) {
      router.push("/login");
    }
  }

  const res = await updateInfo(state.form);

  if (res.status === 200) {
    alert("회원 정보가 수정되었습니다.");
    state.isEditMode = false;
    // 최신 정보로 갱신 (선택 사항)
    await loadData();
  } else {
    alert("수정에 실패했습니다. 다시 시도해주세요.");
  }
};

// 회원 탈퇴
const deleteInfo = async () => {
  if (confirm("정말로 탈퇴하시겠습니까? 이 작업은 되돌릴 수 없습니다.")) {
    const res = await withdraw(accountStore.id);
    if (res.status === 200) {
      alert("탈퇴가 완료되었습니다.");
      accountStore.setLoggedIn(false);
      router.push("/");
    } else {
      alert("탈퇴 처리에 실패했습니다.");
    }
  }
};

// 홈으로 이동
const goHome = () => {
  router.push("/");
};

// 데이터 로드
const loadData = async () => {
  const info = await getInfo();
  if (info.status === 200) {
    state.form = info.data;
    state.original = { ...info.data };
  } else {
    alert("회원 정보를 불러올 수 없습니다.");
    router.push("/login");
  }
};

// 초기 실행
(async function onCreated() {
  await loadData();
})();
</script>

<template>
  <div class="info-page py-5 bg-light">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8">

          <div class="card shadow-sm border-0">
            <div class="card-header bg-white py-4 text-center border-0">
              <h4 class="mb-0 fw-bold">내 정보 관리</h4>
              <p class="text-muted small mt-2 mb-0">
                {{ state.isEditMode ? '수정할 정보를 입력하고 저장 버튼을 눌러주세요.' : '회원 정보를 확인합니다.' }}
              </p>
            </div>

            <div class="card-body p-4 p-md-5">
              <form @submit.prevent="submitUpdate">

                <h6 class="text-primary mb-3 fw-bold">기본 정보</h6>

                <div class="mb-3">
                  <label for="name" class="form-label text-muted small">이름</label>
                  <input type="text" class="form-control" id="name"
                         v-if="state.isEditMode" v-model="state.form.name">
                  <div class="form-control bg-light border-0" v-else>{{ state.form.name }}</div>
                </div>

                <div class="mb-4">
                  <label for="address" class="form-label text-muted small">주소</label>
                  <input type="text" class="form-control" id="address"
                         v-if="state.isEditMode" v-model="state.form.address">
                  <div class="form-control bg-light border-0" v-else>{{ state.form.address }}</div>
                </div>

                <hr class="my-4 text-muted opacity-25">

                <h6 class="text-primary mb-3 fw-bold">계정 정보</h6>

                <div class="mb-3">
                  <label for="loginId" class="form-label text-muted small">아이디</label>
                  <input type="text" class="form-control" id="loginId"
                         v-if="state.isEditMode" v-model="state.form.loginId">
                  <div class="form-control bg-light border-0 text-muted" v-else>{{ maskedLoginId }}</div>
                </div>

                <div class="mb-4">
                  <label for="loginPw" class="form-label text-muted small">비밀번호</label>
                  <input type="text" class="form-control" id="loginPw"
                         v-if="state.isEditMode" v-model="state.form.loginPw">
                  <div class="form-control bg-light border-0 text-muted" v-else>{{ maskedLoginPw }}</div>
                </div>

                <div class="d-grid gap-2 mt-5">
                  <template v-if="state.isEditMode">
                    <button type="submit" class="btn btn-primary py-3 fw-bold shadow-sm">저장 완료</button>
                    <button type="button" class="btn btn-outline-secondary py-3" @click="cancelEdit">취소</button>
                  </template>

                  <template v-else>
                    <button type="button" class="btn btn-primary py-3 fw-bold shadow-sm" @click="startEdit">정보 수정하기</button>
                    <button type="button" class="btn btn-outline-secondary py-3" @click="goHome">홈으로</button>
                  </template>
                </div>

              </form>
            </div>

            <div class="card-footer bg-light py-3 text-center border-0">
              <a href="#" class="text-danger small fw-bold text-decoration-underline" @click.prevent="deleteInfo">회원탈퇴</a>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.info-page {
  min-height: 80vh;
}
.card {
  border-radius: 1rem;
}
/* 조회 모드일 때 텍스트 박스 스타일 */
.form-control.bg-light {
  font-weight: 500;
  color: #333;
}
</style>