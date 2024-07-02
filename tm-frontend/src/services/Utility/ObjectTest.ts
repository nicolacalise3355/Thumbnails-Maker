// eslint-disable-next-line @typescript-eslint/ban-types
export const isEmpty = (obj: Object) => {
    return Object.keys(obj).length === 0;
}

/**
 * 
 * @param text 
 * @returns True if Parsable 
 * Does not check if this is real JSON, just if parsable
 */
export const isJsonParsable = (text: string) => {
  try {
      JSON.parse(text);
      return true;
  } catch (error) {
      return false;
  }
};