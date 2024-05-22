import { IFoundationData } from '@/services/foundation'
import { ReactNode, createContext, useContext, useState } from 'react'

interface FoundationContextType {
  foundation: IFoundationData | undefined
  onFoundationChange: (foundation: IFoundationData | undefined) => void
}

export const FoundationContext = createContext<FoundationContextType>(
  {} as FoundationContextType,
)

export const useFoundationContext = () => useContext(FoundationContext)

interface FoundationContextProviderProps {
  children: ReactNode
}

export const FoundationContextProvider: React.FC<FoundationContextProviderProps> = ({
  children,
}) => {
  const [foundation, setFoundation] = useState<IFoundationData | undefined>()


  return (
    <FoundationContext.Provider
      value={{
        foundation,
        onFoundationChange: setFoundation,
      }}
    >
      {children}
    </FoundationContext.Provider>
  )
}
