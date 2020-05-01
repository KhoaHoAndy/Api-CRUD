using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Threading.Tasks;
using DinhKhoalab3.Data;
using DinhKhoalab3.Models;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;

namespace DinhKhoalab3.Controllers
{
    public class ApplicationUserController : Controller
    {
        private readonly ApplicationDbContext _context;
        private readonly UserManager<ApplicationUser> _userManager;
        private readonly ILogger _logger;

        public ApplicationUserController(ApplicationDbContext context,
            UserManager<ApplicationUser> userManager,
            ILogger<ApplicationUserController> logger)
        {
            _context = context;
            _userManager = userManager;
            _logger = logger;
        }
        
    [HttpGet]
        public async Task<IActionResult> Details(string id)
        {
            if (String.IsNullOrEmpty(id))
            {
                return NotFound();
            }

            ApplicationUser user = await _userManager.FindByIdAsync(id);
            return View(user);
        }

        [HttpGet]
        public async Task<IActionResult> Update(string id)
        {
            if (String.IsNullOrEmpty(id))
            {
                return NotFound();
            }

            ApplicationUser user = await _userManager.FindByIdAsync(id);

            return View(user);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Update(string id, [Bind("Id,Name,Email,Program,FullName,PhoneNumber,Address,DateOfBirth,Age")] ApplicationUser model)
        {
            if (id != model.Id)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    ApplicationUser user = await _userManager.FindByIdAsync(id);
                    user.Name = model.Name;
                    user.Email = model.Email;
                    user.PhoneNumber = model.PhoneNumber;
                    user.Address = model.Address;
                    user.Age = model.Age;
                    user.Program = model.Program;
                    model.SecurityStamp = Guid.NewGuid().ToString("D");
                    user.SecurityStamp = model.SecurityStamp;
                    await _userManager.UpdateAsync(user);

                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!UserExist(model.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                //return RedirectToAction(nameof(Details));
                return RedirectToAction("Details", "ApplicationUser", new { id = model.Id });
            }
            return View(model);
        }
        [HttpGet]
        public async Task<IActionResult> Appointment(string id)
        {
            if (String.IsNullOrEmpty(id))
            {
                return NotFound();
            }
            var user = await _userManager.FindByIdAsync(id);
          
            return View(user);
        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Appointment(string id, [Bind("Id,Description")] ApplicationUser model)
        {
            if (id != model.Id)
            {
                return NotFound();
            }

            if (!ModelState.IsValid)
            {
                try
                {
                    ApplicationUser user = await _userManager.FindByIdAsync(id);
                    user.Description = model.Description;
                    await _userManager.UpdateAsync(user);

                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!UserExist(model.Id))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction("Details", "ApplicationUser", new { id = model.Id });
            }
            return View(model);
        }
        private bool UserExist(string id)
        {
            return _context.ApplicationUser.Any(e => e.Id == id);
        }
    }
}
