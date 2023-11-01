import { expect, test } from "@playwright/test";

test("Navigation", async ({ page }) => {
   await page.goto("http://localhost:3000/");
   await page.click("text=Projects");
   await expect(page).toHaveURL("http://localhost:3000/projects");
   await expect(page.getByText("Projects")).toBeVisible();
});
