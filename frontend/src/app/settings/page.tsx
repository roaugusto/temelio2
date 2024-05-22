'use client'

import { InputControl, InputRoot } from "@/components/Input";
import { ChevronLeft } from "lucide-react";
import { useRouter } from "next/navigation";
import { FormProvider, useForm } from "react-hook-form";

import { TextAreaControl, TextAreaRoot } from "@/components/TextArea";
import { createTemplate, getTemplate, updateTemplate } from "@/services/template";
import { zodResolver } from '@hookform/resolvers/zod';
import { Button } from "@mui/material";
import { useEffect, useState } from "react";
import { z } from 'zod';
import { useFoundationContext } from "@/context/ClientProvider";

const templateFormSchema = z.object({
  name: z.string().min(1, { message: 'Please provide the template name!' }),
  subject: z.string().min(1, { message: 'Please provide the subject!' }),
  template: z.string().min(1, { message: 'Please provide the template!' }),
})

type templateFormData = z.infer<typeof templateFormSchema>
type FormKeys = keyof templateFormData


export default function Settings() {
  const { foundation } = useFoundationContext()
  const router = useRouter()
  const [isNewTemplate, setIsNewTemplate] = useState<boolean>(false)

  const templateForm = useForm<templateFormData>({
    resolver: zodResolver(templateFormSchema),
  })

  const {
    handleSubmit,
    formState: { errors },
    setValue,
  } = templateForm

  useEffect(() => {
    loadTemplate()
  }, [])

  async function loadTemplate() {
    const data = await getTemplate(foundation!.id);
    if (data) {
      fillFormWithExistingData(data)
    } else {
      setIsNewTemplate(true)
    }
  }

  async function fillFormWithExistingData(
    templateData: templateFormData,
  ) {
    const objectKeys = Object.keys(
      templateFormSchema.shape,
    ) as FormKeys[]

    for (const key of objectKeys) {
      if (key in templateData) {
        setValue(key, templateData[key])
      }
    }
  }

  async function onSubmit(data: templateFormData) {
    if (isNewTemplate) {
      const createData = { ...data, foundationId: foundation!.id }
      await createTemplate(createData)
    } else {
      const updateData = { ...data, foundationId: foundation!.id }
      await updateTemplate(updateData)
    }
    router.back()
  }

  return (
    <div>
      <div className="flex mt-2 items-center">
        <ChevronLeft className="cursor-pointer" size={36} onClick={() => router.back()} />
        <h1 className="text-lg lg:text-2xl ml-1">Settings</h1>
      </div>

      <FormProvider {...templateForm}>
        <form
          id="template"
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
              htmlFor="subject"
              className="flex items-center text-sm font-medium text-blue-900"
            >
              Subject
            </label>
            <div className="gap-6">
              <InputRoot>
                <InputControl
                  name="subject"
                  hasError={!!errors.subject}
                  errorMessage={errors.subject?.message}
                ></InputControl>
              </InputRoot>
            </div>
            <label
              htmlFor="template"
              className="flex items-center text-sm font-medium text-blue-900"
            >
              Template
            </label>
            <div className="gap-6">
              <TextAreaRoot>
                <TextAreaControl
                  name="template"
                  hasError={!!errors.template}
                  errorMessage={errors.template?.message}
                  rows={12}
                ></TextAreaControl>
              </TextAreaRoot>
            </div>
          </div>

          <div className="flex justify-end">
            <Button className="text-xs text-center" variant="contained" type="submit">
              Save
            </Button>

          </div>
        </form>
      </FormProvider>


    </div>
  );
}