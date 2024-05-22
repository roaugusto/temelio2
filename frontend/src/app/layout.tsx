'use client'

import { Header } from "@/components/Header";
import { Inter } from "next/font/google";
import { Suspense } from "react";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import "./globals.css";
import { FoundationContextProvider } from "@/context/ClientProvider";

const inter = Inter({ subsets: ["latin"] });

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <head>
        <title>Temelio Nonprofit Management</title>
        <link rel="icon" href="/favicon.png" type="image/png" />
      </head>
      <body className={inter.className}>
        <Suspense fallback={<div>Loading...</div>}>
          <FoundationContextProvider>
            <div className="flex flex-col min-h-screen">
              <Header />
              <div className="p-4">
                {children}
              </div>
            </div>
          </FoundationContextProvider>
        </Suspense>
        <ToastContainer />
      </body>
    </html>
  );
}
