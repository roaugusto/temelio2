'use client'

import { InputControl, InputRoot } from "@/components/Input";
import { useFoundationContext } from "@/context/ClientProvider";
import { getFoundationByEmail } from "@/services/foundation";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function Home() {
  const router = useRouter()
  const { onFoundationChange } = useFoundationContext()
  const [email, setEmail] = useState("")

  async function handleEmailChange(e: React.ChangeEvent<HTMLInputElement>) {
    setEmail(e.target.value)
  }

  async function handleLogin() {

    const foundation = await getFoundationByEmail(email);

    if (foundation === null) {
      return;
    }

    onFoundationChange(foundation)
    router.push('/organization')
  }

  return (
    <div className="min-h-screen flex flex-col items-center">
      <div className="w-full max-w-md p-6 bg-white shadow-md rounded-md mt-40">
        <h1 className="text-2xl font-semibold text-center mb-4">Login</h1>

        <div className="flex flex-col gap-4">
          <div className="flex flex-col gap-1">
            <label htmlFor="email" className="text-sm font-medium text-gray-700">
              E-mail
            </label>
            <InputRoot>
              <InputControl
                name="email"
                value={email}
                onChange={handleEmailChange}
              />
            </InputRoot>
          </div>

          <button
            className="w-full py-2 mt-4 bg-blue-600 text-white font-semibold rounded-md hover:bg-blue-700"
            onClick={handleLogin}
          >
            Login
          </button>
        </div>

        <div className="mt-6 text-center">
          <Link href="/foundation-add">
            <div className="text-blue-600 hover:underline">Create a Foundation</div>
          </Link>
        </div>
      </div>
    </div>
  );
}
