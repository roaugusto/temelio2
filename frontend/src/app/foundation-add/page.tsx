'use client'

import { InputControl, InputRoot } from "@/components/Input";
import { ChevronLeft } from "lucide-react";
import { useRouter, useSearchParams } from "next/navigation";
import { FormProvider, useForm } from "react-hook-form";

import { createFoundation, getFoundation, updateFoundation } from "@/services/foundation";
import { zodResolver } from '@hookform/resolvers/zod';
import { useEffect, useState } from "react";
import { z } from 'zod';

const foundationFormSchema = z.object({
  name: z.string().min(1, { message: 'Please provide the foundation name!' }),
  email: z.string().email({ message: 'Invalid email!' }),
})

type foundationFormData = z.infer<typeof foundationFormSchema>
type FormKeys = keyof foundationFormData


export default function Foundation() {
  const router = useRouter()
  const searchParams = useSearchParams()
  const id = searchParams.get('id')

  const [isNewFoundation, setIsNewFoundation] = useState<boolean>(false)

  const foundationForm = useForm<foundationFormData>({
    resolver: zodResolver(foundationFormSchema),
  })

  const {
    handleSubmit,
    formState: { errors },
    setValue,
  } = foundationForm

  useEffect(() => {
    loadFoundation()
  }, [])

  async function loadFoundation() {
    if (id === null) {
      setIsNewFoundation(true)
      return
    }

    const data = await getFoundation(id);
    if (data) {
      fillFormWithExistingData(data)
    }
  }

  async function fillFormWithExistingData(
    foundationData: foundationFormData,
  ) {
    const objectKeys = Object.keys(
      foundationFormSchema.shape,
    ) as FormKeys[]

    for (const key of objectKeys) {
      if (key in foundationData) {
        setValue(key, foundationData[key])
      }
    }
  }

  async function onSubmit(data: foundationFormData) {
    if (isNewFoundation) {
      await createFoundation(data)
    } else {
      if (id === null) return
      const updateData = { ...data, id }
      await updateFoundation(updateData)
    }
    router.back()
  }

  return (
    <div>
      <div className="flex mt-2 items-center">
        <ChevronLeft className="cursor-pointer" size={36} onClick={() => router.back()} />
        <h1 className="text-lg lg:text-2xl ml-1">Foundation</h1>
      </div>

      <FormProvider {...foundationForm}>
        <form
          id="foundation"
          className="al mb-3 mt-3 flex w-full flex-col gap-2"
          onSubmit={handleSubmit(onSubmit)}
        >
          <div className="flex gap-3 border p-4 rounded-md flex-col bg-white">
            <label
              htmlFor="name"
              className="flex items-center text-sm font-medium text-blue-900"
            >
              Name
            </label>
            <div className="gap-6">
              <InputRoot>
                <InputControl
                  name="name"
                  hasError={!!errors.name}
                  errorMessage={errors.name?.message}
                ></InputControl>
              </InputRoot>
            </div>
            <label
              htmlFor="email"
              className="flex items-center text-sm font-medium text-blue-900"
            >
              E-mail
            </label>
            <div className="gap-6">
              <InputRoot>
                <InputControl
                  name="email"
                  hasError={!!errors.email}
                  errorMessage={errors.email?.message}
                ></InputControl>
              </InputRoot>
            </div>
          </div>

          <div className="flex justify-end">
            <button
              className="rounded-lg border bg-blue-700 px-2 py-1 font-semibold text-white shadow-sm hover:bg-blue-800"
              type="submit"
            >
              Save
            </button>
          </div>
        </form>
      </FormProvider>


    </div>
  );
}