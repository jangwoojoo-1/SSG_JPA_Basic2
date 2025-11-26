<script setup>
import {getItems, removeItem, removeAllItem} from "@/services/cartService"
import {reactive, computed} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const state = reactive({
  items: [],
});

// 총 결제 예상 금액 계산
const computedTotalPrice = computed(() => {
  return state.items.reduce((total, i) => {
    return total + (i.price - i.price * i.discountPer / 100);
  }, 0);
});

// 장바구니 로드
const load = async () => {
  const res = await getItems();

  if (res.status === 200) {
    state.items = res.data;
  } else if (res.status === 401) {
    alert("로그인이 필요합니다.");
    router.push("/login");
  }
}

// 개별 삭제
const remove = async (itemId) => {
  if(!confirm("해당 상품을 삭제하시겠습니까?")) return;
  const res = await removeItem(itemId);
  if (res.status === 200) {
    await load();
  }
}

// 전체 삭제
const removeAll = async() =>{
  if(!confirm("장바구니를 비우시겠습니까?")) return;
  const res = await removeAllItem();
  if (res.status === 200) {
    await load();
  }
}

(async function onCreated() {
  await load();
})();
</script>

<template>
  <div class="cart py-5 bg-light">
    <div class="container">

      <div class="d-flex justify-content-between align-items-center mb-4">
        <h4 class="fw-bold mb-0">
          장바구니 <span class="text-primary fs-5">({{ state.items.length }})</span>
        </h4>
        <button v-if="state.items.length" class="btn btn-outline-secondary btn-sm rounded-pill px-3" @click="removeAll">
          전체 삭제
        </button>
      </div>

      <div class="row" v-if="state.items.length">
        <div class="col-lg-8">
          <ul class="list-group shadow-sm mb-4">
            <li class="list-group-item p-4 border-0 border-bottom" v-for="i in state.items" :key="i.id">
              <div class="d-flex align-items-center">
                <div class="img-box me-4 rounded overflow-hidden cursor-pointer" @click="router.push(`/items/${i.id}`)">
                  <img :src="i.imgPath" :alt="i.name">
                </div>

                <div class="flex-grow-1">
                  <div class="d-flex justify-content-between align-items-start mb-2">
                    <h5 class="fw-bold mb-0 cursor-pointer hover-text-primary" @click="router.push(`/items/${i.id}`)">
                      {{ i.name }}
                    </h5>
                    <button class="btn-close" aria-label="Close" @click="remove(i.id)"></button>
                  </div>

                  <p class="text-muted small mb-2 text-truncate" style="max-width: 400px;">{{ i.comment }}</p>

                  <div class="d-flex align-items-center mt-3">
                    <span class="badge bg-danger me-2 rounded-pill" v-if="i.discountPer > 0">{{ i.discountPer }}%</span>
                    <span class="fw-bold fs-5">{{ (i.price - i.price * i.discountPer / 100).toLocaleString() }}원</span>
                    <span class="text-decoration-line-through text-muted ms-2 small" v-if="i.discountPer > 0">
                      {{ i.price.toLocaleString() }}원
                    </span>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>

        <div class="col-lg-4">
          <div class="card shadow-sm border-0 sticky-top" style="top: 2rem; z-index: 1;">
            <div class="card-body p-4">
              <h5 class="fw-bold mb-4">결제 예정 금액</h5>

              <div class="d-flex justify-content-between mb-3">
                <span class="text-muted">총 상품 금액</span>
                <span>{{ computedTotalPrice.toLocaleString() }}원</span>
              </div>
              <div class="d-flex justify-content-between mb-3">
                <span class="text-muted">배송비</span>
                <span>0원</span>
              </div>

              <hr class="my-4 opacity-25">

              <div class="d-flex justify-content-between align-items-center mb-4">
                <span class="fw-bold">총 합계</span>
                <span class="text-primary fw-bold fs-3">{{ computedTotalPrice.toLocaleString() }}원</span>
              </div>

              <router-link to="/order" class="btn btn-primary w-100 py-3 fw-bold shadow-sm">주문하기</router-link>
            </div>
          </div>
        </div>
      </div>

      <div class="text-center py-5" v-else>
        <div class="mb-4 text-muted" style="font-size: 5rem;">🛒</div>
        <h4 class="fw-bold mb-3">장바구니가 비어있습니다.</h4>
        <p class="text-muted mb-4">원하는 상품을 담아보세요!</p>
        <router-link to="/" class="btn btn-primary px-5 py-3 rounded-pill fw-bold shadow-sm">쇼핑하러 가기</router-link>
      </div>

    </div>
  </div>
</template>

<style lang="scss" scoped>
.cart {
  min-height: 80vh; /* 화면 꽉 차게 */
}

.list-group-item {
  transition: background-color 0.2s;

  &:hover {
    background-color: #fcfcfc;
  }

  .img-box {
    width: 100px;
    height: 100px;
    flex-shrink: 0;
    background-color: #f1f1f1;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .cursor-pointer {
    cursor: pointer;
  }

  .hover-text-primary:hover {
    color: #0d6efd; /* Bootstrap primary color */
  }
}
</style>