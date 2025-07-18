<template>
  <a-form :model="formState" ref="formRef" @finish="handleOk">
    <a-form-item label="产品名" name="name" :rules="[{ required: true, message: '请输入产品名' }]">
      <a-input v-model:value="formState.name" />
    </a-form-item>

    <a-form-item label="产品规格" name="specification">
      <a-input v-model:value="formState.specification" />
    </a-form-item>

    <a-form-item label="类型" name="type" :rules="[{ required: true, message: '请选择产品类型' }]">
      <a-select v-model:value="formState.type" placeholder="请选择产品类型">
        <a-select-option v-for="value in Object.values(ProductType)" :key="value" :value="value">
          {{ $translateProductType(value) }}
        </a-select-option>
      </a-select>
    </a-form-item>

    <a-form-item
      label="分类"
      name="categoryId"
      :rules="[{ required: true, message: '请选择分类' }]"
    >
      <a-select
        v-model:value="formState.categoryId"
        show-search
        placeholder="请选择分类"
        optionFilterProp="label"
      >
        <a-select-option
          v-for="category in categories"
          :key="category.id"
          :value="category.id"
          :label="category.name"
        >
          {{ category.name }}
        </a-select-option>
      </a-select>
    </a-form-item>

    <a-form-item
      label="条形码"
      name="barcode"
      :rules="[{ required: true, message: '请输入产品条形码' }]"
    >
      <a-input v-model:value="formState.barcode" />
    </a-form-item>

    <a-form-item label="单位" name="unit" :rules="[{ required: true, message: '请选择单位' }]">
      <a-select v-model:value="formState.unit" placeholder="请选择单位">
        <a-select-option v-for="value in Object.values(ProductUnit)" :key="value" :value="value">
          {{ $translateProductUnit(value) }}
        </a-select-option>
      </a-select>
    </a-form-item>

    <a-form-item label="备注" name="note">
      <a-textarea v-model:value="formState.note" :rows="3" placeholder="请输入备注" />
    </a-form-item>

    <a-form-item :wrapper-col="{ offset: 8, span: 16 }">
      <a-space>
        <a-button type="primary" html-type="submit">提交</a-button>
        <a-button @click="handleCancel">取消</a-button>
      </a-space>
    </a-form-item>
  </a-form>
</template>

<script lang="ts" setup>
import { inject, reactive, ref } from "vue";
import { type FormInstance, message } from "ant-design-vue";
import { type CreateProductInput, ProductApi, ProductType, ProductUnit } from "@/core/openapi";
import { useMutation, useQuery } from "@tanstack/vue-query";

const formRef = ref<FormInstance>();

const formState = reactive<Partial<CreateProductInput>>({
  name: undefined,
  type: undefined,
  barcode: undefined,
  specification: undefined,
  categoryId: undefined,
  unit: ProductUnit.Item,
  note: undefined
});

const productApi = inject("productApi") as ProductApi;

const emit = defineEmits(["success", "update:visible"]);

const { data: categories } = useQuery({
  queryKey: ["categories"],
  queryFn: () => productApi.listSelectableProductCategories()
});

const { mutate: createProduct } = useMutation({
  mutationFn: (newProduct: CreateProductInput) =>
    productApi.addProduct({ createProductInput: { ...newProduct } }),
  onSuccess: () => {
    message.success("添加成功");
    resetForm();
    emit("success"); // 通知父组件添加成功
    closeDrawer();
  },
  onError: (error) => {
    message.error("添加失败");
    console.error(error);
  }
});

const handleCancel = () => {
  resetForm();

  closeDrawer();
};

const closeDrawer = () => {
  emit("update:visible", false); // 触发 v-model:visible 改变
};

const resetForm = () => {
  formRef.value?.resetFields();
};

const handleOk = () => {
  formRef.value
    ?.validate()
    .then(() => {
      createProduct(formRef.value?.getFieldsValue() as CreateProductInput);
    })
    .catch((error) => {
      console.error("表单验证失败", error);
    });
};
</script>
