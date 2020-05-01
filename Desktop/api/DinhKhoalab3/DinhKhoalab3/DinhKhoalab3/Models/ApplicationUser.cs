using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace DinhKhoalab3.Models
{
    public class ApplicationUser : IdentityUser
    {
        public string Address { get; set; }

        public string Age { get; set; }

        public string UserTypes { get; set; }

        
        public string Name { get; set; }


        override
        public string PhoneNumber { get; set; }

        public string Program { get; set; }

        public string Description { get; set; }


        public string Skills { get; set; }

        public string Languages { get; set; }

        public string Education { get; set; }

        public string Experience { get; set; }

        public string Prefertime { get; set; }

        public string StudentComments { get; set; }
    }
}
