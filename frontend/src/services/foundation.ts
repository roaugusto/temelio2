import { toastMessage } from "@/util/toastMessage";
import { ApiInterface, apiHost } from "./apiConfig"

export interface ICreateFoundationData {
  name: string;
  email: string;
}

export interface IFoundationData {
  id: string;
  name: string;
  email: string;
}

export async function getFoundations(): Promise<IFoundationData[]> {
  try {
    const res = await fetch(`${apiHost}/foundations`);

    if (!res.ok) {
      toastMessage("Error when searching for foundations", "error");
    }

    const resApi = await res.json() as ApiInterface;

    if (!resApi.data) {
      throw new Error("API response does not contain 'date' field");
    }

    return resApi.data as IFoundationData[];
  } catch (error) {
    console.error("Error when searching for foundations:", error);
    throw error;
  }
}

export async function getFoundation(id: string): Promise<IFoundationData | null> {
  try {
    const res = await fetch(`${apiHost}/foundations/${id}`);

    if (!res.ok) {
      toastMessage("Error when searching for foundation", "error");
    }

    const resApi = await res.json() as ApiInterface;

    if (!resApi.data) {
      return null;
    }

    return resApi.data as IFoundationData;
  } catch (error) {
    console.error("Error when searching for foundation:", error);
    throw error;
  }
}

export async function getFoundationByEmail(email: string): Promise<IFoundationData | null> {
  try {
    const res = await fetch(`${apiHost}/foundations/email/${email}`);

    if (!res.ok) {
      toastMessage("Error when searching for foundation", "error");
    }

    const resApi = await res.json() as ApiInterface;

    if (!resApi.data) {
      toastMessage("Foundation not found", "error");
      return null;
    }

    return resApi.data as IFoundationData;
  } catch (error) {
    console.error("Error when searching for foundation:", error);
    throw error;
  }
}

export async function createFoundation(data: ICreateFoundationData): Promise<void> {
  try {
    const res = await fetch(`${apiHost}/foundations`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!res.ok) {
      toastMessage("Error when creating foundation", "error");
      return
    }

    toastMessage("Foundation created successfully", "success");
  } catch (error) {
    console.error("Error when creating foundation:", error);
    throw error;
  }
}

export async function updateFoundation(data: IFoundationData): Promise<void> {

  if (!data.id) {
    toastMessage("Error when updating foundation", "error");
    return
  }

  try {
    const res = await fetch(`${apiHost}/foundations/${data.id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!res.ok) {
      toastMessage("Error when updating foundation", "error");
      return
    }

    toastMessage("Foundation updated successfully", "success");
  } catch (error) {
    console.error("Error when updating foundation:", error);
    throw error;
  }
}

export async function deleteFoundation(id: string): Promise<void> {
  try {
    const res = await fetch(`${apiHost}/foundations/${id}`, {
      method: "DELETE",
    });

    if (!res.ok) {
      toastMessage("Error when deleting foundation", "error");
      return
    }

    toastMessage("Foundation deleted successfully", "success");
  } catch (error) {
    console.error("Error when deleting foundation:", error);
    throw error;
  }
}